package joshie.mariculture.modules.vanilla.asm;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.InsnList;

import static joshie.mariculture.modules.vanilla.asm.ASMType.VISITOR;

public abstract class AbstractASM {

    //Return true if the class name is valid
    public abstract boolean isClass(String name);

    //Return whether this class is applying this type of asm
    public boolean isValidASMType(ASMType type) {
        return type == VISITOR;
    }

    //Only Called when the ASMType is VISITOR
    public ClassVisitor newInstance(String name, ClassWriter writer) {
        return newInstance(writer);
    }

    //Only Called when the ASMType is VISITOR
    public ClassVisitor newInstance(ClassWriter writer) {
        return null;
    }

    //Only called when the ASMType is OVERRIDE
    public String[] getMethodNameAndDescription() {
        return new String[0];
    }

    //Only called when the ASMType is OVERRIDE
    public void addInstructions(ObfType type, InsnList list) {}

    //Only called when the ASMType is TRANSFORM
    public byte[] transform(byte[] modified) {
        return modified;
    }
}