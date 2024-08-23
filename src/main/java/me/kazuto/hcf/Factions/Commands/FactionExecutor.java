/* (Copyright) 2024 github.com/kaz2700 */
package me.kazuto.hcf.Factions.Commands;

import me.kazuto.hcf.Factions.Utils.ArgumentExecutor;

public class FactionExecutor extends ArgumentExecutor {
	public FactionExecutor() {
		addArgument(new FCreateArgument());
		addArgument(new FWhoArgument());
		addArgument(new FDisbandArgument());
		addArgument(new FListArgument());
		addArgument(new FInviteArgument());
		addArgument(new FJoinArgument());
		addArgument(new FUninviteArgument());
		addArgument(new FOpenArgument());
		addArgument(new FCloseArgument());
		addArgument(new FClaimArgument());
		addArgument(new FLeaveArgument());
		addArgument(new FMapArgument());
		addArgument(new FDepositArgument());
		addArgument(new FWithdrawArgument());
		addArgument(new FAnnouncementArgument());
		addArgument(new FSetHomeArgument());
		addArgument(new FHomeArgument());
		addArgument(new FUnclaimArgument());
		addArgument(new FPromoteArgument());
		addArgument(new FDemoteArgument());
		addArgument(new FKickArgument());
		addArgument(new FLeaderArgument());
	}
}
// todo if command is only f then show hel.p
