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
  }
}
// todo if command is only f then show hel.p
