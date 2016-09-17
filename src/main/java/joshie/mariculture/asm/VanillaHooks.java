package joshie.mariculture.asm;

import joshie.mariculture.asm.hooks.ASMFishHook;
import net.minecraft.launchwrapper.IClassTransformer;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin.MCVersion;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@MCVersion("1.10.2")
public class VanillaHooks implements IFMLLoadingPlugin, IClassTransformer {
    private static List<AbstractASM> asm = new ArrayList<>();

    static {
        asm.add(new ASMFishHook());
    }

    @Override
    public byte[] transform(String name, String transformedName, byte[] data) {
        byte[] modified = data;
        for (AbstractASM a : asm) {
            if (a.isClass(name)) {
                if (a.isValidASMType(ASMType.VISITOR)) modified = ASMHelper.visit(name, a, data);
                if (a.isValidASMType(ASMType.OVERRIDE)) modified = ASMHelper.override(a, data);
                if (a.isValidASMType(ASMType.TRANSFORM)) modified = a.transform(data);
            }
        }

        return modified;
    }

    @Override
    public String[] getASMTransformerClass() {
        return new String[] { VanillaHooks.class.getName() };
    }

    @Override
    public String getModContainerClass() {
        return null;
    }

    @Override
    public String getSetupClass() {
        return null;
    }

    @Override
    public void injectData(Map<String, Object> data) {}

    @Override
    public String getAccessTransformerClass() {
        return null;
    }
}
