
package ru.otus;


import org.objectweb.asm.*;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.lang.invoke.*;
import java.security.ProtectionDomain;
import java.util.ArrayList;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.commons.Method;

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
        ArrayList<String> list = new ArrayList<>();
        ClassVisitor visitor = new ClassVisitor(Opcodes.ASM5, writer) {

            @Override
            public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions){
                System.out.println("visitMethod: access="+access+" name="+name+" desc="+descriptor+" signature="+signature+" exceptions="+exceptions);
                Method thisMethod = new Method(name, descriptor);

                MethodVisitor mv = new MethodAnnotationScanner(Opcodes.ASM5, super.visitMethod(access, name, descriptor, signature, exceptions), thisMethod, className);
                return mv;
            }
        };

        reader.accept(visitor, Opcodes.ASM5);

        for(String methodName : list) {
            System.out.println(methodName);
        }

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

        private Method thisMethod;
        private boolean isChangeMethod = false;
        private String className = null;
        private StringBuilder descriptor = new StringBuilder("(");

        public MethodAnnotationScanner(int api, MethodVisitor methodVisitor, Method thisMethod, String className) {
            super(api, methodVisitor);
            this.thisMethod = thisMethod;
            this.className = className;
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
                super.visitVarInsn(Opcodes.ALOAD, 0);
                    int i = 1;
                    for(Type arg : thisMethod.getArgumentTypes()) {
                        this.descriptor.append(arg.getDescriptor());
                        if (arg.getDescriptor().equals("J")) {
                            super.visitVarInsn(Opcodes.LLOAD, i);
                            ++i;
                        } else if (arg.getDescriptor().equals("D")) {
                            super.visitVarInsn(Opcodes.DLOAD, i);
                            ++i;
                        } else if (arg.getDescriptor().equals("F")) {
                            super.visitVarInsn(Opcodes.FLOAD, i);
                        } else if(arg.getDescriptor().equals("I")) {
                            super.visitVarInsn(Opcodes.ILOAD, i);
                        }
                        i++;
                    }

                    Handle handle = new Handle(
                            H_INVOKESTATIC,
                            Type.getInternalName(java.lang.invoke.StringConcatFactory.class),
                            "makeConcatWithConstants",
                            MethodType.methodType(CallSite.class, MethodHandles.Lookup.class, String.class, MethodType.class, String.class, Object[].class).toMethodDescriptorString(),
                            false);
                    this.descriptor.append(")Ljava/lang/String;");
                    super.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
                    super.visitInvokeDynamicInsn("makeConcatWithConstants", this.descriptor.toString(), handle, "executed method: " + this.thisMethod.getName()  + ", param: \u0001".repeat(i));
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
