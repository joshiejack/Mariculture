package mariculture.api.fishery.fish;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import mariculture.core.helpers.cofh.StringHelper;
import mariculture.core.lib.Text;
import mariculture.fishery.FishHelper;
import net.minecraft.item.ItemStack;

import org.lwjgl.input.Keyboard;

public class FishDNA extends FishDNABase {
	public ArrayList<FishDNA> types;
	private boolean isHidden = false;
	private boolean isToggled = false;
	public String name;
	public int minimum;
	public int maximum;
	public boolean isDominant;
	
	@Override
	public FishDNA register() {
		types = new ArrayList();
		DNAParts.add(this);
		return this;
	}
	
	public void add(String name, int min, int max, boolean dominant) {
		types.add(new FishDNA().setValues(name, min, max, dominant));
	}
	
	//Sets whether this dna should be displayed underneath a fish
	public FishDNA setHidden() {
		this.isHidden = true;
		return this;
	}
	
	public FishDNA setShift() {
		this.isToggled = true;
		return this;
	}
	
	public FishDNA setValues(String name, int min, int max, boolean dominant) {
		this.name = name;
		this.minimum = min;
		this.maximum = max;
		this.isDominant = dominant;
		return this;
	}

	@Override
	public void getInformationDisplay(ItemStack stack, List list) {
		if(isHidden) return;
		else if(!isToggled || (isToggled && Keyboard.isKeyDown(Keyboard.KEY_LSHIFT))) {
			int type = stack.getTagCompound().getInteger(getHigherString());
			for(FishDNA dna: types) {
				if(type >= dna.minimum && type <= dna.maximum) {
					String data = Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)? "" + getDNA(stack): Text.localize("fish.data." + getHigherString().toLowerCase() + "." + dna.name);
					list.add(Text.localize("fish.data." + getHigherString().toLowerCase()) + ": " + data);
				}
			}
		}
	}
	
	public String getDNAName(ItemStack stack) {
		int type = stack.getTagCompound().getInteger(getHigherString());
		for(FishDNA dna: types) {
			if(type >= dna.minimum && type <= dna.maximum) {
				return Text.localize("fish.data." + getHigherString().toLowerCase() + "." + dna.name);
			}
		}
		
		return "";
	}
	
	public String getLowerDNAName(ItemStack stack) {
		int type = stack.getTagCompound().getInteger(getLowerString());
		for(FishDNA dna: types) {
			if(type >= dna.minimum && type <= dna.maximum) {
				return Text.localize("fish.data." + getHigherString().toLowerCase() + "." + dna.name);
			}
		}
		
		return "";
	}
	
	@Override
	public int[] getDominant(int option1, int option2, Random rand) {
		int dominance1 = 0;
		int dominance2 = 0;
		
		for(FishDNA dna: types) {
			if(option1 >= dna.minimum && option1 <= dna.maximum) {
				dominance1 = (dna.isDominant) ? 0: 1;
			}
			
			if(option2 >= dna.minimum && option2 <= dna.maximum) {
				dominance2 = (dna.isDominant)? 0: 1;
			}
		}

		return FishHelper.swapDominance(dominance1, dominance2, option1, option2, rand);
	}
	
	@Override
	public String[] getScannedDisplay(ItemStack stack) {
		boolean is1Dominant = false;
		boolean is2Dominant = false;
		int option1 = getDNA(stack);
		int option2 = getLowerDNA(stack);
		for(FishDNA dna: types) {
			if(option1 >= dna.minimum && option1 <= dna.maximum) {
				is1Dominant = dna.isDominant;
			}
			
			if(option2 >= dna.minimum && option2 <= dna.maximum) {
				is2Dominant = dna.isDominant;
			}
		}
		
		String display1 = (is1Dominant? Text.ORANGE: Text.INDIGO) + getDNA(stack) + " (" + getDNAName(stack) + ")";
		String display2 = (is2Dominant? Text.ORANGE: Text.INDIGO) + getLowerDNA(stack) + " (" + getLowerDNAName(stack) + ")";
		return new String[] { Text.localize("fish.data." + getName().toLowerCase()), display1, display2 };
	}
}
