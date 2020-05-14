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

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import static org.objectweb.asm.Opcodes.H_INVOKESTATIC;


public class Agent {

    public static void premain(String agentArgs, Instrumentation inst) {

        inst.addTransformer(new ClassFileTransformer() {
            @Override
            public byte[] transform(ClassLoader loader,
                                    String className,
                                    Class<?> classBeingRedefined,
                                    ProtectionDomain protectionDomain,
                                    byte[] classfileBuffer) throws IllegalClassFormatException {
                    if(className.contains("ru/otus/")) {
                        return changeMethod(classfileBuffer);
                    }
                    return classfileBuffer;
            }
        });
    }

    private static byte[] changeMethod(byte[] originalClass) {
        ClassReader reader = new ClassReader(originalClass);
        ClassWriter writer = new ClassWriter(reader, ClassWriter.COMPUTE_MAXS);
        ClassVisitor visitor = new ClassVisitor(Opcodes.ASM5, writer) {

            @Override
            public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions){
//                System.out.println("visitMethod: access="+access+" name="+name+" desc="+descriptor+" signature="+signature+" exceptions="+exceptions);
                MethodVisitor mv = new MethodAnnotationScanner(Opcodes.ASM5, super.visitMethod(access, name, descriptor, signature, exceptions), name);
                return mv;
            }
        };

        reader.accept(visitor, Opcodes.ASM5);

//        byte[] finalClass = writer.toByteArray();
//
//        try (OutputStream fos = new FileOutputStream("TestLogging.class")) {
//            fos.write(finalClass);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        return writer.toByteArray();
    }

    static class MethodAnnotationScanner extends MethodVisitor {

        private String methodName = null;
        private boolean isChangeMethod = false;

        public MethodAnnotationScanner(int api, MethodVisitor methodVisitor, String methodName) {
            super(api, methodVisitor);
            this.methodName = methodName;
        }

        @Override
        public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
//            System.out.println("visitAnnotation: desc="+desc+" visible="+visible);
            if(desc.contains("ru/otus/annotations/Log")) {
                this.isChangeMethod = true;
                return super.visitAnnotation(desc, visible);
            }
            this.isChangeMethod = false;
            return super.visitAnnotation(desc, visible);
        }

        @Override
        public void visitCode() {
            if(this.isChangeMethod) {
                Handle handle = new Handle(
                        H_INVOKESTATIC,
                        Type.getInternalName(java.lang.invoke.StringConcatFactory.class),
                        "makeConcatWithConstants",
                        MethodType.methodType(CallSite.class, MethodHandles.Lookup.class, String.class, MethodType.class, String.class, Object[].class).toMethodDescriptorString(),
                        false);
                super.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
                super.visitVarInsn(Opcodes.ILOAD, 1);
                super.visitInvokeDynamicInsn("makeConcatWithConstants", "(I)Ljava/lang/String;", handle, "executed method: " + this.methodName  +", param:\u0001");
                super.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
                super.visitMaxs(0, 0);
            }

            if (mv != null) {
                super.visitCode();
            }
            super.visitEnd();
        }
    }

}
