package com.github.apohrebniak.tmail.core.storage;

import com.github.apohrebniak.tmail.core.CoreProperties;
import com.github.apohrebniak.tmail.core.UserRegistry;
import com.github.apohrebniak.tmail.core.domain.MailboxUserIds;
import com.github.apohrebniak.tmail.core.domain.UserRecord;
import com.hazelcast.core.IMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class HazelcastUserRegistry implements UserRegistry {

    @Autowired
    private CoreProperties coreProperties;
    @Autowired
    private IMap<Long, String> userToMailboxMap;

    @Override
    public void add(MailboxUserIds pair) {
        userToMailboxMap.put(pair.getTelegramId(), pair.getMailboxId(),
                coreProperties.getTtl(), TimeUnit.MINUTES);
    }

    @Override
    public boolean exists(Long userId) {
        return userToMailboxMap.containsKey(userId);
    }

    @Override
    public Optional<UserRecord> getUserRecordById(Long id) {
        return Optional.ofNullable(userToMailboxMap.getEntryView(id))
                .map(view -> new UserRecord(view.getKey(), view.getValue(), view.getTtl()));
    }
}
