package mariculture.fishery;

import java.util.Arrays;
import java.util.Map;

import net.minecraft.launchwrapper.IClassTransformer;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.TypeInsnNode;

import com.google.common.eventbus.EventBus;

import cpw.mods.fml.common.DummyModContainer;
import cpw.mods.fml.common.LoadController;
import cpw.mods.fml.common.ModMetadata;
import cpw.mods.fml.common.asm.transformers.deobf.FMLDeobfuscatingRemapper;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin;

public class VanillaOverride extends DummyModContainer implements IFMLLoadingPlugin, IClassTransformer {	
	public VanillaOverride() {
		super(new ModMetadata());
		ModMetadata meta = getMetadata();
		meta.modId = "MCVanillaTweaks";
		meta.name = "Mariculture - Vanilla Tweaks";
		meta.version = "1.0";
		meta.description = "Fishies! This mod overwrites the vanilla mechanics";
		meta.authorList = Arrays.asList("joshie");
		meta.url = "http://mariculture.wikispaces.com";
		meta.screenshots = new String[0];
		meta.parent = "mariculture";
	}

	@Override
	public boolean registerBus(EventBus bus, LoadController controller) {
		bus.register(this);
		
		return true;
	}
	
	@Override
	public String[] getASMTransformerClass() {
		return new String[]{ VanillaOverride.class.getName() };
	}

	@Override
	public String getModContainerClass() {
		return VanillaOverride.class.getName();
	}

	@Override
	public String getSetupClass() {
		return null;
	}

	@Override
	public void injectData(Map<String, Object> data) {
		
	}

	@Override
	public String getAccessTransformerClass() {
		return null;
	}
	
	private FMLDeobfuscatingRemapper remapper;

	@Override
	public byte[] transform(String className, String arg1, byte[] data) {
		remapper = FMLDeobfuscatingRemapper.INSTANCE;
		byte[] newData = data;
		
		if(className.equals("net.minecraft.item.abn")) {
			System.out.println("[Mariculture] Patching the vanilla class: " + className);
			newData = patchFishingStuff(data, true);
			if(newData != data) {
				System.out.println("[Mariculture] Succesfully replaced Vanilla Fishing Rod and Fish");
			} else {
				System.out.println("[Mariculture] Failed to replace Vanilla Fishing Rod and Fish");
			}
		}

		if(className.equals("net.minecraft.item.Item")) {
			System.out.println("[Mariculture] Patching the vanilla class: " + className);
			newData = patchFishingStuff(data, false);
			if(newData != data) {
				System.out.println("[Mariculture] Succesfully replaced Vanilla Fishing Rod and Fish");
			} else {
				System.out.println("[Mariculture] Failed to replace Vanilla Fishing Rod and Fish");
			}
		}

		return newData;
	}

	public byte[] patchFishingStuff(byte[] data, boolean obfuscated) {
		String classBlock = obfuscated ? "net/minecraft/item/abn" : "net/minecraft/item/Item";		
		String registerItems = obfuscated ? "l" : "registerItems";
		
		ClassNode classNode = new ClassNode();
		ClassReader classReader = new ClassReader(data);
		classReader.accept(classNode, 0);
		
		boolean rodFound = false;
		boolean fishFound = false;
		
		for(int i = 0; i < classNode.methods.size(); i++) {
			MethodNode method = classNode.methods.get(i);
			if(method.name.equals(registerItems) && method.desc.equals("()V")) {				
				for(int j = 0; j < method.instructions.size(); j++) {					
					AbstractInsnNode instruction = method.instructions.get(j);
					if(instruction.getType() == AbstractInsnNode.LDC_INSN) {
						LdcInsnNode ldcInstruction = (LdcInsnNode)instruction;
						if(ldcInstruction.cst.equals("fishing_rod")) {							
							if(!rodFound) {
								((TypeInsnNode)method.instructions.get(j + 1)).desc = "mariculture/fishery/items/ItemVanillaRod";
								((MethodInsnNode)method.instructions.get(j + 3)).owner = "mariculture/fishery/items/ItemVanillaRod";
							}
							
							rodFound = true;
						} else if (ldcInstruction.cst.equals("fish")) {
							if(!fishFound) {
								((TypeInsnNode)method.instructions.get(j + 1)).desc = "mariculture/fishery/items/ItemVanillaFish";
								((MethodInsnNode)method.instructions.get(j + 4)).owner = "mariculture/fishery/items/ItemVanillaFish";
							}
							
							fishFound = true;
						}
					}
				}
			}
		}

		ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS);
		classNode.accept(writer);
		return writer.toByteArray();
	}
}