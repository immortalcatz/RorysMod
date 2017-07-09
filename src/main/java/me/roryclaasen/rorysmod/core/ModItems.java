/*
Copyright 2016-2017 Rory Claasen

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */
package me.roryclaasen.rorysmod.core;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import ic2.api.item.IC2Items;
import ic2.api.recipe.RecipeInputItemStack;
import ic2.api.recipe.Recipes;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import me.roryclaasen.rorysmod.item.ItemBase;
import me.roryclaasen.rorysmod.item.ItemDust;
import me.roryclaasen.rorysmod.item.ItemIngot;
import me.roryclaasen.rorysmod.item.ItemPlate;
import me.roryclaasen.rorysmod.item.ItemRifle;
import me.roryclaasen.rorysmod.item.ItemRifleUpgrade;
import me.roryclaasen.rorysmod.register.Register;
import me.roryclaasen.rorysmod.util.RMLog;
import me.roryclaasen.rorysmod.util.registry.ItemRegistry;

public class ModItems implements TypeGroup {

	public ItemRegistry registry = new ItemRegistry();

	public static Item steelIngot, steelDust, steelPlate;
	public static Item carbonIngot;
	public static Item rifle1, rifle2, rifle3, rifle4, rifle5;
	public static Item laserBolt, rifleBarrel, rifleTrigger;
	public static Item rifleUpgrade, upgradePlate;
	public static Item circuit, advancedCircuit;
	public static Item lens, filament, cpu;

	@Override
	public void preInit(FMLPreInitializationEvent event) {
		steelIngot = new ItemIngot("ingotSteel");
		steelDust = new ItemDust("dustSteel");
		steelPlate = new ItemPlate("plateSteel");
		carbonIngot = new ItemIngot("ingotCarbon");
		rifle1 = new ItemRifle("rifle1", 1);
		rifle2 = new ItemRifle("rifle2", 2);
		rifle3 = new ItemRifle("rifle3", 3);
		rifle4 = new ItemRifle("rifle4", 4);
		rifle5 = new ItemRifle("rifle5", 5);
		rifleBarrel = new ItemBase("rifleBarrel");
		rifleTrigger = new ItemBase("rifleTrigger");
		laserBolt = new ItemBase("laser").setCreativeTab(null);
		rifleUpgrade = new ItemRifleUpgrade("rifleUpgrade");
		upgradePlate = new ItemPlate("plateUpgrade");
		circuit = new ItemBase("circuit");
		advancedCircuit = new ItemBase("advancedCircuit");
		lens = new ItemBase("lens");
		filament = new ItemBase("filament");
		cpu = new ItemBase("cpu");
	}

	@Override
	public void register(FMLPreInitializationEvent event) {
		RMLog.info("Registering Items");

		Register.registerItem(steelIngot);
		Register.registerItem(steelDust);
		Register.registerItem(steelPlate);
		Register.registerItem(carbonIngot);
		Register.registerItem(rifle1);
		Register.registerItem(rifle2);
		Register.registerItem(rifle3);
		Register.registerItem(rifle4);
		Register.registerItem(rifle5);
		Register.registerItem(rifleBarrel);
		Register.registerItem(rifleTrigger);
		Register.registerItem(laserBolt);
		Register.registerItem(rifleUpgrade);
		Register.registerItem(upgradePlate);
		Register.registerItem(circuit);
		Register.registerItem(advancedCircuit);
		Register.registerItem(lens);
		Register.registerItem(filament);
		Register.registerItem(cpu);

		Register.registerDictionary("ingotSteel", steelIngot);
		Register.registerDictionary("dustSteel", steelDust);
		Register.registerDictionary("plateSteel", steelPlate);
		Register.registerDictionary("ingotCarbon", carbonIngot);
		Register.registerDictionary("circuitBasic", circuit);
		Register.registerDictionary("circuitAdvanced", advancedCircuit);
		Register.registerDictionary("lens", lens);
		Register.registerDictionary("filament", filament);
		Register.registerDictionary("cpu", cpu);

		if (Loader.isModLoaded("NotEnoughItems")) {
			//API.hideItem(new ItemStack(laserBolt));
		}
	}

	@Override
	public void createRecipes() {
		ItemStack forgeHammer = IC2Items.getItem("ForgeHammer").copy();
		forgeHammer.setItemDamage(OreDictionary.WILDCARD_VALUE);

		// Carbon
		Register.addShaplessRecipie(new ItemStack(carbonIngot), IC2Items.getItem("carbonFiber"), IC2Items.getItem("carbonFiber"), IC2Items.getItem("carbonFiber"), "ingotIron");
		Register.addSmeltingRecipie(new ItemStack(carbonIngot), new ItemStack(steelIngot), 0.1f);

		// Steel
		Register.addShaplessRecipie(new ItemStack(steelDust, 2), IC2Items.getItem("carbonFiber"), IC2Items.getItem("carbonFiber"), IC2Items.getItem("carbonFiber"), "dustIron");
		Register.addShaplessRecipie(new ItemStack(steelPlate), "ingotSteel", forgeHammer);
		Register.addShaplessRecipie(new ItemStack(steelIngot, 9), "blockSteel");
		Register.addSmeltingRecipie(new ItemStack(steelDust), new ItemStack(steelIngot), 0.1f);
		Recipes.metalformerRolling.addRecipe(new RecipeInputItemStack(new ItemStack(steelIngot)), null, new ItemStack(steelPlate));
		Recipes.macerator.addRecipe(new RecipeInputItemStack(new ItemStack(steelPlate)), null, new ItemStack(steelDust));

		// Circuit
		Register.addShapedRecipie(new ItemStack(circuit), new Object[]{" r ", "gsg", " r ", 'r', Items.redstone, 's', "plateSteel", 'g', Items.gold_nugget});
		Register.addShapedRecipie(new ItemStack(advancedCircuit), new Object[]{"lrg", "cpc", "grl", 'l', new ItemStack(Items.dye, 1, 4), 'r', Items.redstone, 'g', Items.glowstone_dust, 'p', "cpu", 'c', "circuitBasic"});
		Register.addShapedRecipie(new ItemStack(advancedCircuit), new Object[]{"grl", "cpc", "lrg", 'l', new ItemStack(Items.dye, 1, 4), 'r', Items.redstone, 'g', Items.glowstone_dust, 'p', "cpu", 'c', "circuitBasic"});

		// Lens
		Register.addShapedRecipie(new ItemStack(lens, 4), new Object[]{" g ", "g g", " g ", 'g', Blocks.glass});

		// Filament
		Register.addShaplessRecipie(new ItemStack(filament, 2), new Object[]{Items.redstone, Items.flint, IC2Items.getItem("copperCableItem")});
		// CPU
		Register.addShapedRecipie(new ItemStack(cpu), new Object[]{" r ", "rcr", " r ", 'r', Items.redstone, 'c', "circuitBasic"});

		// Rifle
		Register.addShapedRecipie(new ItemStack(rifleBarrel), new Object[]{"sss", "   ", "sss", 's', "ingotSteel"});
		Register.addShapedRecipie(new ItemStack(rifleTrigger), new Object[]{" ss", " s ", "  s", 's', "ingotSteel"});
		Register.addShapedRecipie(new ItemStack(rifle1), new Object[]{"lbe", "ssc", " ts", 'l', "lens", 'b', new ItemStack(rifleBarrel), 'e', IC2Items.getItem("energyCrystal"), 's', "plateSteel", 'c', "circuitAdvanced", 't', new ItemStack(rifleTrigger)});

		// Rifle upgrade
		Register.addShapedRecipie(new ItemStack(upgradePlate, 2), new Object[]{"rir", "nsn", "rir", 'r', Items.redstone, 'i', "ingotIron", 'n', Items.gold_nugget, 's', "plateSteel"});
		Register.addShapedRecipie(new ItemStack(rifleUpgrade), new Object[]{"cuc", 'u', new ItemStack(upgradePlate), 'c', "circuitBasic"});
		Register.addShapedRecipie(new ItemStack(rifleUpgrade, 1, 1), new Object[]{"c", "b", 'b', new ItemStack(rifleUpgrade), 'c', IC2Items.getItem("reBattery")});
		Register.addShapedRecipie(new ItemStack(rifleUpgrade, 1, 2), new Object[]{"w", "b", 'b', new ItemStack(rifleUpgrade), 'w', IC2Items.getItem("waterCell")});
		// Register.addShapedRecipie(new ItemStack(rifleUpgrade, 1, 3), new Object[]{"l", "b", 'b', new ItemStack(rifleUpgrade), 'l', "lens"}));
		Register.addShapedRecipie(new ItemStack(rifleUpgrade, 1, 4), new Object[]{"f", "b", 'b', new ItemStack(rifleUpgrade), 'f', filament});
		Register.addShapedRecipie(new ItemStack(rifleUpgrade, 1, 5), new Object[]{"c", "b", 'b', new ItemStack(rifleUpgrade), 'c', cpu});
		Register.addShapedRecipie(new ItemStack(rifleUpgrade, 1, 6), new Object[]{"t", "b", 'b', new ItemStack(rifleUpgrade), 't', Blocks.tnt});
		Register.addShapedRecipie(new ItemStack(rifleUpgrade, 1, 7), new Object[]{"f", "b", 'b', new ItemStack(rifleUpgrade), 'f', Items.flint_and_steel});
	}
}