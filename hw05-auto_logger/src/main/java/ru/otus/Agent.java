
package ru.otus;

// java -javaagent:/home/test/Projects/otus_java_2020_03/hw05-auto_logger/build/libs/loggerHW05-0.1.jar -jar /home/test/Projects/otus_java_2020_03/hw05-auto_logger/build/libs/loggerHW05-0.1.jar

import org.objectweb.asm.*;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.lang.invoke.*;
import java.lang.reflect.Method;
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
                        return changeMethod(classfileBuffer, className);
                    }
                    return classfileBuffer;
            }
        });
    }

    private static byte[] changeMethod(byte[] originalClass, String className) {
        ClassReader reader = new ClassReader(originalClass);
        ClassWriter writer = new ClassWriter(reader, ClassWriter.COMPUTE_MAXS);
        ClassVisitor visitor = new ClassVisitor(Opcodes.ASM5, writer) {

            @Override
            public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions){
                MethodVisitor mv = new MethodAnnotationScanner(Opcodes.ASM5, name, descriptor,
                        super.visitMethod(access, name, descriptor, signature, exceptions));
                return mv;
            }
        };

        reader.accept(visitor, Opcodes.ASM5);

        byte[] finalClass = writer.toByteArray();

        if(className.contains("Test")) {
            try (OutputStream fos = new FileOutputStream("TestLogging.class")) {
                fos.write(finalClass);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return writer.toByteArray();
    }

    static class MethodAnnotationScanner extends MethodVisitor {

        private boolean isChangeMethod = false;
        private final String name, descriptor;

        public MethodAnnotationScanner(int api, String name, String methodDescriptor, MethodVisitor methodVisitor) {
            super(api, methodVisitor);
            this.name = name;
            this.descriptor = methodDescriptor;
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
                super.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
                int varIndex = 1, numArgs = 0, p;
                for(p = 1; descriptor.charAt(p) != ')'; p++) {
                    switch(descriptor.charAt(p)) {
                        case 'J':
                            super.visitVarInsn(Opcodes.LLOAD, varIndex); ++varIndex; break;
                        case 'D':
                            super.visitVarInsn(Opcodes.DLOAD, varIndex); ++varIndex; break;
                        case 'F': super.visitVarInsn(Opcodes.FLOAD, varIndex); break;
                        case 'I': super.visitVarInsn(Opcodes.ILOAD, varIndex); break;
                        case 'L': super.visitVarInsn(Opcodes.ALOAD, varIndex);
                            p = descriptor.indexOf(';', p);
                            break;
                        case '[': super.visitVarInsn(Opcodes.ALOAD, varIndex);
                            do {} while(descriptor.charAt(++p)=='[');
                            if(descriptor.charAt(p) == 'L') p = descriptor.indexOf(';', p);
                            break;
                        default: throw new IllegalStateException(descriptor);
                    }
                    varIndex++;
                    numArgs++;
                }

                String ret = "Ljava/lang/String;";
                String concatSig = new StringBuilder(++p + ret.length())
                        .append(descriptor, 0, p).append(ret).toString();

                Handle handle = new Handle(
                        H_INVOKESTATIC,
                        "java/lang/invoke/StringConcatFactory",
                        "makeConcatWithConstants",
                        MethodType.methodType(CallSite.class, MethodHandles.Lookup.class,
                                String.class, MethodType.class, String.class, Object[].class)
                                .toMethodDescriptorString(),
                        false);
                super.visitInvokeDynamicInsn("makeConcatWithConstants", concatSig, handle,
                        "executed method: " + name + ", param: \u0001".repeat(numArgs));
                super.visitMethodInsn(Opcodes.INVOKEVIRTUAL,
                        "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
            }
                super.visitCode();
        }
    }

}
