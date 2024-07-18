package org.example.webserver.util.idgenerator;

import java.util.concurrent.atomic.AtomicLong;

public class AtomicLongIdGenerator implements IdGenerator<Long> {

    private final AtomicLong generator = new AtomicLong();

    @Override
    public Long getNext() {
        return generator.incrementAndGet();
    }
}
