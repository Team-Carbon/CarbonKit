package net.teamcarbon.carbonkit.modules;

import net.teamcarbon.carbonkit.CarbonKit;
import net.teamcarbon.carbonkit.CarbonKit.ConfType;
import net.teamcarbon.carbonkit.commands.CarbonCrafting.UncraftCommand;
import net.teamcarbon.carbonkit.utils.Messages.Clr;
import net.teamcarbon.carbonkit.utils.TypeUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.ItemMeta;
import net.teamcarbon.carbonkit.utils.DuplicateModuleException;
import net.teamcarbon.carbonkit.utils.Module;
import net.teamcarbon.carbonkit.utils.MiscUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@SuppressWarnings("UnusedDeclaration")
public class CarbonCraftingModule extends Module {

	public enum UncraftResult { SUCCESS, FAIL_NO_RECIPE, FAIL_FURNACE_RECIPE, FAIL_DURABILITY, FAIL_NO_ITEM, FAIL_FULL_INV, FAIL_DISABLED }

	public CarbonCraftingModule() throws DuplicateModuleException { super(CarbonKit.inst, "CarbonCrafting", "ccrafting", "carboncraft", "ccraft", "cc"); }
	public static CarbonCraftingModule inst;
	public void initModule() {
		inst = this;
		addCmd(new UncraftCommand(this));
		loadRecipes();
		registerListeners();
	}
	public void disableModule() {
		unregisterListeners();
	}
	public void reloadModule() {
		disableModule();
		CarbonKit.inst.reloadConfig();
		CarbonKit.reloadConfig(ConfType.DATA);
		initModule();
	}
	protected boolean needsListeners() { return true; }
	
	/*=============================================================*/
	/*===[                     LISTENERS                       ]===*/
	/*=============================================================*/

	@EventHandler
	public void interact(PlayerInteractEvent e) {
		if (!isEnabled()) return;
		Player pl = e.getPlayer();
		if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			Block b = e.getClickedBlock();
			if (b.getType() == Material.WORKBENCH) {
				if (pl.isSneaking()) {
					UncraftResult ur = uncraft(pl);
					switch (ur) {
						case SUCCESS:
							pl.sendMessage(getMsg("uncraft-success", true));
							break;
						case FAIL_NO_RECIPE:
							pl.sendMessage(getMsg("uncraft-fail-no-recipe", true));
							break;
						case FAIL_FURNACE_RECIPE:
							pl.sendMessage(getMsg("uncraft-fail-furnace-recipe", true));
							break;
						case FAIL_DURABILITY:
							pl.sendMessage(getMsg("uncraft-fail-durability", true));
							break;
						case FAIL_NO_ITEM:
							pl.sendMessage(getMsg("uncraft-fail-no-item", true));
							break;
						case FAIL_FULL_INV:
							pl.sendMessage(getMsg("uncraft-fail-full-inv", true));
							break;
						case FAIL_DISABLED:
							pl.sendMessage(getMsg("uncraft-fail-disabled", true));
							break;
					}
				}
			}
		}
	}
	
	/*=============================================================*/
	/*===[                      METHODS                        ]===*/
	/*=============================================================*/

	public static UncraftResult uncraft(Player pl) {
		if (!inst.getConfig().getBoolean("allow-uncrafting", true)) return UncraftResult.FAIL_DISABLED;
		if (pl == null || pl.getInventory().getItemInMainHand() == null || pl.getInventory().getItemInMainHand().getType() == Material.AIR) return UncraftResult.FAIL_NO_ITEM;
		ItemStack is = pl.getInventory().getItemInMainHand();
		if (is.getDurability() < is.getType().getMaxDurability()) return UncraftResult.FAIL_DURABILITY;
		List<Recipe> recipes = Bukkit.getRecipesFor(is);
		if (recipes == null || recipes.isEmpty()) return UncraftResult.FAIL_NO_RECIPE;
		for (Recipe r : new ArrayList<>(recipes)) { if (r instanceof FurnaceRecipe) recipes.remove(r); }
		if (recipes.isEmpty()) return UncraftResult.FAIL_FURNACE_RECIPE;
		Recipe r = recipes.get(0);
		int req = r.getResult().getAmount();
		List<ItemStack> ing = new ArrayList<>();
		if (r instanceof ShapelessRecipe) { ing = new ArrayList<>(((ShapelessRecipe) r).getIngredientList()); }
		else if (r instanceof ShapedRecipe) {
			for (ItemStack ris : ((ShapedRecipe) r).getIngredientMap().values()) { ing.add(ris); }
		} else { CarbonKit.log.warn("Uncraft recipe is not shaped or shapeless"); }
		int emptySlots = 0;
		for (int i = 0; i < pl.getInventory().getSize(); i++) {
			ItemStack item = pl.getInventory().getItem(i);
			if (item == null || item.getType() == Material.AIR) emptySlots++;
		}
		if (ing.size() > emptySlots) return UncraftResult.FAIL_FULL_INV;
		pl.getInventory().setItem(pl.getInventory().getHeldItemSlot(), new ItemStack(Material.AIR));
		for (ItemStack item : ing) { if (item != null) pl.getInventory().addItem(new ItemStack(item.getType())); }
		return UncraftResult.SUCCESS;
	}

	private static ItemStack applyData(String path, ItemStack is) {
		ItemStack item = is.clone();
		if (inst.getConfig().contains(path)) {
			ConfigurationSection sect = inst.getConfig().getConfigurationSection(path);
			ItemMeta im = item.getItemMeta();
			item.setAmount(sect.getInt("amount", 1));
			item.setDurability((short)sect.getInt("durability", 0));
			if (sect.contains("name") && !sect.getString("name").isEmpty())
				im.setDisplayName(Clr.trans(sect.getString("name")));
			if (sect.contains("lore") && !sect.getStringList("lore").isEmpty()) {
				List<String> lore = sect.getStringList("lore");
				if (!lore.isEmpty()) {
					List<String> cLore = new ArrayList<>();
					for (String s : lore) if (!s.isEmpty()) cLore.add(Clr.trans(s));
					im.setLore(cLore);
				}
			}
			if (sect.contains("enchants")) {
				if (!sect.getConfigurationSection("enchants").getKeys(false).isEmpty()){
					HashMap<Enchantment, Integer> enchants = new HashMap<>();
					for (String e : sect.getConfigurationSection("enchants").getKeys(false)) {
						int lvl = sect.getInt("enchants." + e, 1);
						if (MiscUtils.getEnchant(e) != null)
							enchants.put(MiscUtils.getEnchant(e), lvl);
						else
							CarbonKit.log.warn("Invalid enchant: " + e + " for item: " + is.getType().name() + ", skipping enchantment");
					}
					if (enchants.size() > 0)
						for (Enchantment ench : enchants.keySet())
							im.addEnchant(ench, enchants.get(ench), true);
				}
			}
			item.setItemMeta(im);
		}
		return item;
	}

	@SuppressWarnings("deprecation")
	public static void loadRecipes() {
		ConfigurationSection shaped = inst.getConfig().getConfigurationSection("shaped-recipes");
		ConfigurationSection shapeless = inst.getConfig().getConfigurationSection("shapeless-recipes");
		ConfigurationSection furnace = inst.getConfig().getConfigurationSection("furnace-recipes");
		for (String r : shaped.getKeys(false)) {
			Material isMat = MiscUtils.getMaterial(r);
			if (isMat != null) {
				ItemStack item = new ItemStack(isMat);
				item = applyData("shaped-recipes." + r, item);
				ShapedRecipe recipe = new ShapedRecipe(item);
				char[] chars = new char[]{'A','B','C','D','E','F','G','H','I'};
				Material[] mats = new Material[9];
				String[] rows = new String[3];
				for (int i = 0; i < 3; i++) {
					String row = shaped.getStringList(r + ".recipe").get(i);
					String[] items = row.split(",");
					for (int j = 0; j < items.length; j++)
						mats[i*3+j] = MiscUtils.getMaterial(items[j]);
				}
				for (int c = 0; c < 3; c++) {
					String row = "";
					for (int d = 0; d < 3; d++) {
						if (mats[c*3+d] == null) row += " ";
						else row += chars[c*3+d];
					}
					rows[c] = row;
				}
				recipe.shape(rows);
				for (int e = 0; e < 9; e++)
					if (rows[0].contains(chars[e]+"") || rows[1].contains(chars[e]+"") || rows[2].contains(chars[e]+""))
						recipe.setIngredient(chars[e], mats[e]);
				Bukkit.addRecipe(recipe);
				//addedRecipes.add(recipe);
			} else {
				CarbonKit.log.warn("Invalid item: " + r + ", unable to register shaped recipe");
			}
		}
		for (String r : furnace.getKeys(false)) {
			Material isMat = MiscUtils.getMaterial(r);
			if (isMat != null) {
				if (furnace.contains(r + ".source") && MiscUtils.getMaterial(furnace.getString(r + ".source")) != null) {
					ItemStack item = new ItemStack(isMat);
					Material source = MiscUtils.getMaterial(furnace.getString(r + ".source"));
					if (source != null) {
						item = applyData("furnace-recipes." + r, item);
						FurnaceRecipe recipe = new FurnaceRecipe(item, source);
						Bukkit.addRecipe(recipe);
						//addedRecipes.add(recipe);
					}
				} else {
					CarbonKit.log.warn("Invalid furnace source for item: " + r + ", unable to register furnace recipe");
				}
			} else {
				CarbonKit.log.warn("Invalid item: " + r + ", unable to register furnace recipe");
			}
		}
		nextRecipe:for (String r : shapeless.getKeys(false)) {
			Material isMat = MiscUtils.getMaterial(r);
			if (isMat != null) {
				ItemStack item = new ItemStack(isMat);
				item = applyData("shapeless-recipes." + r, item);
				ShapelessRecipe recipe = new ShapelessRecipe(item);
				if (shapeless.contains(r + ".recipe") && !shapeless.getStringList(r + ".recipe").isEmpty()) {
					int mats = 0;
					for (String s : shapeless.getStringList(r + ".recipe")) {
						String[] ing = s.split(" ");
						int amount = 1, data = 0;
						Material mat;
						if (ing.length == 1) {
							if (MiscUtils.getMaterial(ing[0]) != null)
								mat = MiscUtils.getMaterial(ing[0]);
							else {
								CarbonKit.log.warn("Invalid ingredient: " + ing[0] + " in item: " + r + ", unable to register shapeless recipe");
								continue nextRecipe;
							}
						} else if (ing.length == 2) {
							if (TypeUtils.isInteger(ing[0])) {
								amount = Integer.parseInt(ing[0]);
								if (MiscUtils.getMaterial(ing[1]) != null)
									mat = MiscUtils.getMaterial(ing[1]);
								else {
									CarbonKit.log.warn("Invalid ingrdient: " + s + " in item: " + r + ", unable to register shapeless recipe");
									continue nextRecipe;
								}
							} else if (MiscUtils.getMaterial(ing[0]) != null)
								mat = MiscUtils.getMaterial(ing[0]);
							else {
								CarbonKit.log.warn("Invalid ingrdient: " + s + " in item: " + r + ", unable to register shapeless recipe");
								continue nextRecipe;
							}
							if (mat != null && TypeUtils.isInteger(ing[1]))
								data = Integer.parseInt(ing[1]);
							if (mat == null) {
								CarbonKit.log.warn("Invalid ingrdient: " + s + " in item: " + r + ", unable to register shapeless recipe");
								continue nextRecipe;
							}
						} else if (ing.length == 3) {
							if (TypeUtils.isInteger(ing[0])) {
								amount = Integer.parseInt(ing[0]);
							} else {
								CarbonKit.log.warn("Invalid ingrdient: " + s + " in item: " + r + ", unable to register shapeless recipe");
								continue nextRecipe;
							}
							if (MiscUtils.getMaterial(ing[1]) != null)
								mat = MiscUtils.getMaterial(ing[1]);
							else {
								CarbonKit.log.warn("Invalid ingrdient: " + s + " in item: " + r + ", unable to register shapeless recipe");
								continue nextRecipe;
							}
							if (TypeUtils.isInteger(ing[2]))
								data = Integer.parseInt(ing[2]);
							else {
								CarbonKit.log.warn("Invalid ingrdient: " + s + " in item: " + r + ", unable to register shapeless recipe");
								continue nextRecipe;
							}
						} else {
							CarbonKit.log.warn("Invalid ingrdient: " + s + " in item: " + r + ", unable to register shapeless recipe");
							continue nextRecipe;
						}
						if (amount > 9 || mats + amount > 9) {
							CarbonKit.log.warn("Too many ingredients in item: " + r + " (can have up to 9), unable to register shapeless recipe");
							continue nextRecipe;
						} else mats += amount;
						if (mat == null) {
							CarbonKit.log.warn("Invalid ingrdient: " + s + " in item: " + r + ", unable to register shapeless recipe");
							continue nextRecipe;
						}
						recipe.addIngredient(amount, mat, data);
					}
				} else {
					CarbonKit.log.warn("No ingredients for item: " + r + ", unable to register shapeless recipe");
				}
				Bukkit.addRecipe(recipe);
				//addedRecipes.add(recipe);
			} else {
				CarbonKit.log.warn("Invalid item: " + r + ", unable to register shapeless recipe");
			}
		}
	}
}