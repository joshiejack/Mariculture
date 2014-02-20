package mariculture.magic;

//TODO: Move Enchantment Ticker to new Events System
/*
public class EnchantUpdateTicker implements IScheduledTickHandler {
	@Override
	public void tickStart(EnumSet<TickType> type, Object... tickData) {
		EntityPlayer player = (EntityPlayer) tickData[0];
		if(!player.worldObj.isRemote) {
			int speed = EnchantHelper.getEnchantStrength(Magic.speed, player);
			int jump = EnchantHelper.getEnchantStrength(Magic.jump, player);
			int glide = EnchantHelper.getEnchantStrength(Magic.glide, player);
			int flight = EnchantHelper.getEnchantStrength(Magic.flight, player);
			int step = EnchantHelper.getEnchantStrength(Magic.stepUp, player);
			boolean spider = EnchantHelper.hasEnchantment(Magic.spider, player);

			EnchantmentSpeed.set(speed);
			EnchantmentJump.set(jump);
			EnchantmentGlide.set(glide);
			EnchantmentFlight.set(flight);
			EnchantmentSpider.set(spider);
			EnchantmentStepUp.set(step, player);

			PacketDispatcher.sendPacketToPlayer(new Packet111UpdateEnchants(speed, jump, glide, flight, spider, step).build(), (Player) player);
			Packets.updatePlayer(player, 128, new Packet105OneRing(player.entityId, player.isInvisible()).build());

			if(EnchantHelper.hasEnchantment(Magic.hungry, player))
				EnchantmentNeverHungry.activate(player);
			if(EnchantHelper.hasEnchantment(Magic.health, player))
				EnchantmentHealth.activate(player);
			if(EnchantHelper.getEnchantStrength(Magic.poison, player) > 2)
				EnchantmentPoison.activate(player);
			if(EnchantHelper.hasEnchantment(Magic.repair, player))
				EnchantmentRestore.activate(player);
		} else {
			if(EnchantHelper.exists(Magic.flight))
				EnchantmentFlight.activate(player);
		}
	}

	@Override
	public void tickEnd(EnumSet<TickType> type, Object... tickData) {

	}

	@Override
	public EnumSet<TickType> ticks() {
		return EnumSet.of(TickType.PLAYER);
	}

	@Override
	public String getLabel() {
		return "Mariculture - Jewelry Enchant Ticker";
	}

	@Override
	public int nextTickSpacing() {
		return Extra.JEWELRY_TICK_RATE;
	}
} */
