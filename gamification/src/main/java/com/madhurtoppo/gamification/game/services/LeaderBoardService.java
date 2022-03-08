package com.madhurtoppo.gamification.game.services;

import com.madhurtoppo.gamification.game.domain.LeaderBoardRow;

import java.util.List;

public interface LeaderBoardService {
    List<LeaderBoardRow> getCurrentLeaderBoard();
}
