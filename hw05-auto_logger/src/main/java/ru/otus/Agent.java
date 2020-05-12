/**
 * Created by Ilya Rogatkin, May 2020
 */

package ru.otus;

// java -javaagent:/home/test/Projects/otus_java_2020_03/hw05-auto_logger/build/libs/loggerHW05-0.1.jar -jar /home/test/Projects/otus_java_2020_03/hw05-auto_logger/build/libs/loggerHW05-0.1.jar

import org.objectweb.asm.*;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.lang.invoke.*;
import java.security.ProtectionDomain;
import java.lang.instrument.ClassFileTransformer;
import java.security.spec.RSAOtherPrimeInfo;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.commons.AdviceAdapter;
import org.objectweb.asm.tree.MethodNode;

import javax.swing.*;

import static org.objectweb.asm.Opcodes.H_INVOKESTATIC;


public class Agent {
    public static void premain(String agentArgs, Instrumentation inst) {

        System.out.println("premain");

        inst.addTransformer(new ClassFileTransformer() {
            @Override
            public byte[] transform(ClassLoader loader,
                                    String className,
                                    Class<?> classBeingRedefined,
                                    ProtectionDomain protectionDomain,
                                    byte[] classfileBuffer) throws IllegalClassFormatException {
                    if(className.contains("ru/otus/")) {
                        return changeMethod(classfileBuffer, className);
                    }
                    return classfileBuffer;
            }
        });
    }

    private static byte[] changeMethod(byte[] originalClass, String cn) {
        ClassReader reader = new ClassReader(originalClass);
        ClassWriter writer = new ClassWriter(reader, ClassWriter.COMPUTE_MAXS);
        ClassVisitor visitor = new ClassVisitor(Opcodes.ASM5, writer) {
            @Override
            public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
                if(name.equals("calculation")) {
                    return super.visitMethod(access, "calculationLog", descriptor, signature, exceptions);
                } else {
                    return super.visitMethod(access, name, descriptor, signature, exceptions);
                }
            }
        };

        reader.accept(visitor, Opcodes.ASM5);

        MethodVisitor mv = writer.visitMethod(Opcodes.ACC_PUBLIC, "calculation", "(I)V", null, null);

        Handle handle = new Handle(
                H_INVOKESTATIC,
                Type.getInternalName(java.lang.invoke.StringConcatFactory.class),
                "makeConcatWithConstants",
                MethodType.methodType(CallSite.class, MethodHandles.Lookup.class, String.class, MethodType.class, String.class, Object[].class).toMethodDescriptorString(),
                false);

        mv.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
        mv.visitVarInsn(Opcodes.ILOAD, 1);
        mv.visitInvokeDynamicInsn("makeConcatWithConstants", "(I)Ljava/lang/String;", handle, "execution method with param:\u0001");

        mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);

        mv.visitVarInsn(Opcodes.ALOAD, 0);
        mv.visitVarInsn(Opcodes.ILOAD, 1);
        mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, cn, "calculationLog", "(I)V", false);

        mv.visitInsn(Opcodes.RETURN);
        mv.visitMaxs(0, 0);
        mv.visitEnd();

        return writer.toByteArray();
    }
}
