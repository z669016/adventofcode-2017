package com.putoet.day9;

public record DataStats(int groupScore, int garbageLength, int excludedCounter) {
    public static DataStats parse(String input) {
        assert input != null;

        final DataTokenizer data = new DataTokenizer(input);

        StringBuilder garbage = null;
        int garbageLevel = 0;
        boolean excludeNext = false;
        int excludedCounter = 0;
        int garbageLength = 0;
        int groupLevel = 0;
        int groupScore = 0;

        while (data.hasNext()) {
            final DataToken token = data.next();
            if (garbageLevel > 0) {
                assert garbage != null;

                garbage.append(token.data());
            }

            if (excludeNext) {
                excludedCounter++;
                excludeNext = false;
            } else {
                switch (token.type()) {
                    case EXCLUDE_NEXT:
                        excludeNext = true;
                        break;

                    case OPEN_GARBAGE:
                        if (garbageLevel == 0) {
                            garbage = new StringBuilder();
                            garbage.append(token.data());
                            garbageLevel = 1;
                        }
                        break;

                    case CLOSE_GARBAGE:
                        if (garbageLevel > 0) {
                            garbageLength += garbage.length() - 2;
                            garbage = null;
                            garbageLevel = 0;
                        }
                        break;

                        case OPEN_GROUP:
                            if (garbageLevel == 0)
                                groupLevel++;
                            break;

                        case CLOSE_GROUP:
                            if (garbageLevel == 0) {
                                groupScore += groupLevel;
                                groupLevel--;
                            }
                            break;
                }
            }
        }

        return new DataStats(groupScore, garbageLength, excludedCounter);
    }
}
