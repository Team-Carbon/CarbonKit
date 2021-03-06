package net.teamcarbon.carbonkit.commands.CarbonTools;

import net.teamcarbon.carbonkit.modules.CarbonToolsModule;
import net.teamcarbon.carbonkit.utils.Messages.Clr;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import net.teamcarbon.carbonkit.utils.Module;
import net.teamcarbon.carbonkit.utils.ModuleCmd;
import org.bukkit.entity.*;

@SuppressWarnings("UnusedDeclaration")
public class AnimalInfoCommand extends ModuleCmd {

	public AnimalInfoCommand(Module module) { super(module, "animalinfo"); }

	@Override
	public void execModCmd(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage(mod.getCoreMsg("not-online", false));
			return;
		}
		if (!mod.perm(sender, "animalinfo")) {
			sender.sendMessage(mod.getMsg("no-perm", false));
			return;
		}
		CarbonToolsModule.pendingAniInfo.add((Player) sender);
		sender.sendMessage(Clr.AQUA + "Right click a mob to view info about it");
	}
}
