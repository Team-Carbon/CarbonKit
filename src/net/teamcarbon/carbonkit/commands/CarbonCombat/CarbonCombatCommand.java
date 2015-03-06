package net.teamcarbon.carbonkit.commands.CarbonCombat;

import net.teamcarbon.carbonkit.utils.Module;
import net.teamcarbon.carbonkit.utils.ModuleCmd;
import net.teamcarbon.carbonlib.Messages.Clr;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

@SuppressWarnings("UnusedDeclaration")
public class CarbonCombatCommand extends ModuleCmd {

	public CarbonCombatCommand(Module module) { super(module, "carboncombat"); }

	@Override
	public void execModCmd(CommandSender sender, Command cmd, String label, String[] args) {
		sender.sendMessage(Clr.RED + "Not Yet Implemented");
	}

}
