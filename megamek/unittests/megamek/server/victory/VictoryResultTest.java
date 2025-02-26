package megamek.server.victory;

import junit.framework.TestCase;
import megamek.common.IGame;
import megamek.common.IPlayer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mockito;


@RunWith(JUnit4.class)
public class VictoryResultTest {

    @Test
    public void testGetWinningPlayer() {
        // Trivial case: no players
        VictoryResult testResult = new VictoryResult(false);
        TestCase.assertSame(IPlayer.PLAYER_NONE, testResult.getWinningPlayer());

        // Case with two players
        int winningPlayer = 0;
        int losingPlayer = 1;

        testResult.addPlayerScore(winningPlayer, 100);
        testResult.addPlayerScore(losingPlayer, 40);

        TestCase.assertSame(winningPlayer, testResult.getWinningPlayer());

        // Case with three players and a draw
        int secondWinningPlayer = 2;

        testResult.addPlayerScore(secondWinningPlayer, 100);
        TestCase.assertNotSame(secondWinningPlayer, testResult.getWinningPlayer());
        TestCase.assertNotSame(winningPlayer, testResult.getWinningPlayer());
        TestCase.assertSame(IPlayer.PLAYER_NONE, testResult.getWinningPlayer());
    }

    @Test
    public void testGetWinningTeam() {
        // Trivial case: no team
        VictoryResult testResult = new VictoryResult(false);
        TestCase.assertSame(IPlayer.TEAM_NONE, testResult.getWinningTeam());

        // Case with two teams
        int winningTeam = 1;
        int losingTeam = 2;

        testResult.addTeamScore(winningTeam, 100);
        testResult.addTeamScore(losingTeam, 40);

        TestCase.assertSame(winningTeam, testResult.getWinningTeam());

        // Case with three teams and a draw
        int secondWinningTeam = 3;

        testResult.addTeamScore(secondWinningTeam, 100);
        TestCase.assertNotSame(secondWinningTeam, testResult.getWinningTeam());
        TestCase.assertNotSame(winningTeam, testResult.getWinningTeam());
        TestCase.assertSame(IPlayer.TEAM_NONE, testResult.getWinningTeam());
    }

    @Test
    public void testProcessVictory() {
        // Trivial cases
        VictoryResult victoryResult = new VictoryResult(true);

        IPlayer playerMock = Mockito.mock(IPlayer.class);
        Mockito.when(playerMock.getColorForPlayer()).thenReturn("");

        IGame gameMock = Mockito.mock(IGame.class);
        Mockito.when(gameMock.getPlayer(Mockito.anyInt())).thenReturn(playerMock);

        TestCase.assertTrue(victoryResult.processVictory(gameMock).isEmpty());

        VictoryResult victoryResult2 = new VictoryResult(false);
        TestCase.assertTrue(victoryResult2.processVictory(gameMock).isEmpty());

        // Less trivial cases
        // Only won player is set
        VictoryResult victoryResult3 = new VictoryResult(true);
        victoryResult3.addPlayerScore(1, 100);
        TestCase.assertSame(1, victoryResult3.processVictory(gameMock).size());

        // Only won team is set
        VictoryResult victoryResult4 = new VictoryResult(true);
        victoryResult4.addTeamScore(1, 100);
        TestCase.assertSame(1, victoryResult4.processVictory(gameMock).size());

        // Both player and team winners are set
        VictoryResult victoryResult5 = new VictoryResult(true);
        victoryResult5.addPlayerScore(1, 100);
        victoryResult5.addTeamScore(1, 100);
        TestCase.assertSame(2, victoryResult5.processVictory(gameMock).size());

        // Draw result
        VictoryResult victoryResult6 = new VictoryResult(true);
        victoryResult6.addPlayerScore(1, 100);
        victoryResult6.addPlayerScore(2, 100);
        TestCase.assertTrue(victoryResult6.processVictory(gameMock).isEmpty());
    }

}
