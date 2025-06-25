package com.f4.commentlike.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class CommentTestSamples {

    private static final Random random = new Random();
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Comment getCommentSample1() {
        return new Comment()
            .id(UUID.fromString("23d8dc04-a48b-45d9-a01d-4b728f0ad4aa"))
            .parentType("parentType1")
            .parentId(UUID.fromString("23d8dc04-a48b-45d9-a01d-4b728f0ad4aa"))
            .userId(UUID.fromString("23d8dc04-a48b-45d9-a01d-4b728f0ad4aa"))
            .likesCount(1)
            .repliesCount(1)
            .mentions(UUID.fromString("23d8dc04-a48b-45d9-a01d-4b728f0ad4aa"));
    }

    public static Comment getCommentSample2() {
        return new Comment()
            .id(UUID.fromString("ad79f240-3727-46c3-b89f-2cf6ebd74367"))
            .parentType("parentType2")
            .parentId(UUID.fromString("ad79f240-3727-46c3-b89f-2cf6ebd74367"))
            .userId(UUID.fromString("ad79f240-3727-46c3-b89f-2cf6ebd74367"))
            .likesCount(2)
            .repliesCount(2)
            .mentions(UUID.fromString("ad79f240-3727-46c3-b89f-2cf6ebd74367"));
    }

    public static Comment getCommentRandomSampleGenerator() {
        return new Comment()
            .id(UUID.randomUUID())
            .parentType(UUID.randomUUID().toString())
            .parentId(UUID.randomUUID())
            .userId(UUID.randomUUID())
            .likesCount(intCount.incrementAndGet())
            .repliesCount(intCount.incrementAndGet())
            .mentions(UUID.randomUUID());
    }
}
