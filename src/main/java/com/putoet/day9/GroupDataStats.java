package com.putoet.day9;

import java.util.ArrayList;
import java.util.List;

public class GroupDataStats {
    private final GroupData data;
    private final List<String> garbageList = new ArrayList<>();
    private int groupLevel = 0;
    private int garbageLevel = 0;
    private long garbageLength = 0;
    private boolean excludeNext = false;
    private long excludedCounter = 0;
    private long groupCounter = 0;
    private long groupScore = 0;

    public GroupDataStats(GroupData data) {
        assert data != null;

        this.data = data;
    }

    public long excludedCounter() { return excludedCounter; }
    public long groupCounter() { return groupCounter; }
    public long groupScore(){ return groupScore; }
    public List<String> garbageList() { return garbageList; }
    public long garbageLength() { return garbageLength; }

    public void parse() {
        StringBuffer garbage = null;
        char token = data.get();

        while (token != GroupData.EOF) {
            if (garbageLevel > 0)
                garbage.append(token);

            if (excludeNext) {
                excludedCounter++;
                excludeNext = false;
            } else {
                switch (token) {
                    case GroupData.EXCLUDE_NEXT:
                        excludeNext = true;
                        break;

                    case GroupData.OPEN_GARBAGE:
                        if (garbageLevel == 0) {
                            garbage = new StringBuffer();
                            garbage.append(token);
                            garbageLevel = 1;
                        }
                        break;

                    case GroupData.CLOSE_GARBAGE:
                        if (garbageLevel > 0) {
                            garbageList.add(garbage.toString());
                            garbageLength += garbage.length() - 2;
                            garbage = null;
                            garbageLevel = 0;
                        }
                        break;

                        case GroupData.OPEN_GROUP:
                            if (garbageLevel == 0)
                                groupLevel++;
                            break;

                        case GroupData.CLOSE_GROUP:
                            if (garbageLevel == 0) {
                                groupCounter++;
                                groupScore += groupLevel;
                                groupLevel--;
                            }
                            break;
                }
            }

            token = data.get();
        }
    }
}
