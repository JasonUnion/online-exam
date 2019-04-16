package com.tangyi.web;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tangyi.constant.Role;
import com.tangyi.domain.Contact;
import com.tangyi.domain.Grade;
import com.tangyi.service.ContactService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 联系人控制层
 * Created by tangyi on 2017-04-16.
 */
@RestController
@RequestMapping("/v1/contacts")
public class ContactController {

    private static Logger logger = LoggerFactory.getLogger(ContactController.class);

    @Autowired
    ContactService contactService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('" + Role.ROLE_TEACHER + "') or hasAuthority('" + Role.ROLE_ADMIN + "')")
    public PageInfo<Grade> getContactList(@RequestParam(required = false) Integer pageIndex,
                                        @RequestParam(required = false) Integer pageSize,
                                        @RequestParam(required = false) Integer limit,
                                        @RequestParam(required = false) Integer offset) {
        if(pageIndex != null && pageSize != null) {
            PageHelper.startPage(pageIndex, pageSize);
        }
        List<Contact> contacts = contactService.getContactList();
        PageInfo pageInfo = new PageInfo(contacts);
        return pageInfo;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('" + Role.ROLE_TEACHER + "') or hasAuthority('" + Role.ROLE_ADMIN + "')")
    public Contact getContact(@PathVariable Long id) {
        return contactService.getContactById(id);
    }

    @RequestMapping(value = "/user/{username}", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('" + Role.ROLE_TEACHER + "') or hasAuthority('" + Role.ROLE_ADMIN + "')")
    public Contact getContact(@PathVariable String username) {
        return contactService.getContactByUsername(username);
    }

    @RequestMapping(value = "/users/{username}", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('" + Role.ROLE_TEACHER + "') or hasAuthority('" + Role.ROLE_ADMIN + "')")
    public List<Contact> getContactList(@PathVariable String username) {
        return contactService.getContactListByUsername(username);
    }

    @RequestMapping(value = "/status", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('" + Role.ROLE_TEACHER + "') or hasAuthority('" + Role.ROLE_ADMIN + "')")
    public int getContactCount(@RequestParam String status) {
        return contactService.getContactCountByStatus(status);
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<?> postContact(@RequestBody Contact contact) {
        contactService.saveContact(contact);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @RequestMapping(value = "", method = RequestMethod.PUT)
    @PreAuthorize("hasAuthority('" + Role.ROLE_TEACHER + "') or hasAuthority('" + Role.ROLE_ADMIN + "')")
    public ResponseEntity<?> putContact(@RequestBody Contact contact) {
        contactService.updateContact(contact);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @PreAuthorize("hasAuthority('" + Role.ROLE_TEACHER + "') or hasAuthority('" + Role.ROLE_ADMIN + "')")
    public ResponseEntity<?> deleteContact(@PathVariable Long id) {
        Contact contact = new Contact();
        contact.setId(id);
        contactService.deleteContact(contact);
        return new ResponseEntity(HttpStatus.OK);
    }
}
