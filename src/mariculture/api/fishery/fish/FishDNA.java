package mariculture.api.fishery.fish;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class FishDNA {
	public static final ArrayList<FishDNA> DNAParts = new ArrayList<FishDNA>();

	public FishDNA() {
		DNAParts.add(this);
	}

	/** The name of the string the egg array saves this in **/
	public String getEggString() {
		return null;
	}

	/** The name of the string to save the Dominant part of the gene as **/
	public String getHigherString() {
		return null;
	}

	/** The name of the string to save the Recessive part of the gene as **/
	public String getLowerString() {
		return null;
	}

	/** Add information about this piece of DNA to the list if necessary **/

	public void getInformationDisplay(ItemStack stack, List list) {
		// Do Nothing
	}

	/** Attempt to cause a mutation **/
	public int[] attemptMutation(int parent1dna, int parent2dna) {
		int[] ret = new int[2];
		ret[0] = parent1dna;
		ret[1] = parent2dna;
		return ret;
	}

	/** return a list of these based on the dominance, dominant goes first **/
	public int[] getDominant(int option1, int option2, Random rand) {
		int[] ret = new int[2];
		ret[0] = option1;
		ret[1] = option2;
		return ret;
	}

	/**
	 * return the data needed for this piece of dna if it's coming from the
	 * species file
	 **/
	public Integer getDNAFromSpecies(FishSpecies species) {
		return -1;
	}
	
	//Everything below this point is mostly irrelevant when adding new dna except in special cases

	/** Automatically called when generating a fish **/
	public ItemStack addDNA(ItemStack stack, Integer data) {
		if (!stack.hasTagCompound()) {
			stack.setTagCompound(new NBTTagCompound());
		}

		stack.stackTagCompound.setInteger(this.getHigherString(), data);

		return stack;
	}

	/** Automatically called when generating a fish **/
	public ItemStack addLowerDNA(ItemStack stack, Integer data) {
		if (!stack.hasTagCompound()) {
			stack.setTagCompound(new NBTTagCompound());
		}

		stack.stackTagCompound.setInteger(this.getLowerString(), data);

		return stack;
	}

	/** Automatically called when generating a fish **/
	public void addDNAList(ItemStack stack, int[] data) {
		if (!stack.hasTagCompound()) {
			stack.setTagCompound(new NBTTagCompound());
		}

		stack.stackTagCompound.setIntArray(this.getEggString(), data);
	}

	/** Automatically called when reading a fish **/
	public Integer getDNA(ItemStack stack) {
		return stack.stackTagCompound.getInteger(this.getHigherString());
	}

	/** Automatically called when reading a fish **/
	public Integer getLowerDNA(ItemStack stack) {
		return stack.stackTagCompound.getInteger(this.getLowerString());
	}

	/** Automatically called when reading a fish **/
	public int[] getDNAList(ItemStack stack) {
		return stack.stackTagCompound.getIntArray(this.getEggString());
	}
}
