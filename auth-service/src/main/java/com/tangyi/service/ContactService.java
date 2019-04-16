package com.tangyi.service;

import com.tangyi.constant.RedisKey;
import com.tangyi.domain.Contact;
import com.tangyi.mapper.ContactMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by tangyi on 2017-04-16.
 */
@Service
@Transactional(readOnly = true)
public class ContactService {

    private static Logger logger = LoggerFactory.getLogger(ContactService.class);

    @Autowired
    ContactMapper contactMapper;

    @Autowired
    CacheService cacheService;

    public List<Contact> getContactList() {
        return contactMapper.getContactList();
    }

    public Contact getContactById(Long id) {
        Contact contact = (Contact) cacheService.get(RedisKey.CONTACT_ID + id);
        if(contact == null) {
            contact = contactMapper.getContactById(id);
            if(contact != null) {
                cacheService.set(RedisKey.CONTACT_ID + id, contact);
            }
        }
        return contact;
    }

    public Contact getContactByUsername(String username) {
        Contact contact = (Contact) cacheService.get(RedisKey.CONTACT_USERNAME + username);
        if(contact == null) {
            contact = contactMapper.getContactByUsername(username);
            if(contact != null) {
                cacheService.set(RedisKey.CONTACT_USERNAME + username, contact);
            }
        }
        return contact;
    }

    public List<Contact> getContactListByUsername(String username) {
        return contactMapper.getContactListByUsername(username);
    }

    public int getContactCountByStatus(String status) {
        return contactMapper.getContactCountByStatus(status);
    }

    @Transactional(readOnly = false)
    public int saveContact(Contact contact) {
        contact.setCreated(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date( )));
        contact.setStatus("false");
        return contactMapper.insert(contact);
    }

    @Transactional(readOnly = false)
    public void updateContact(Contact contact) {
        contactMapper.update(contact);
    }

    @Transactional(readOnly = false)
    public void deleteContact(Contact contact) {
        contactMapper.delete(contact);
    }
}
