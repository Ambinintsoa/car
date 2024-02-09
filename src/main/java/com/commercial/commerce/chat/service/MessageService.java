package com.commercial.commerce.chat.service;

import com.commercial.commerce.chat.model.Message;
import com.commercial.commerce.chat.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.commercial.commerce.chat.model.Message;
import com.commercial.commerce.chat.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class MessageService {
    @Autowired
    private MessageRepository messageRepository;

    public void save(Message message) {
        messageRepository.save(message);
    }

    public List<Message> getNotif(String receiverEmail, Pageable pageable){
        return messageRepository.findByReceiverEmailOrderByDateDesc(receiverEmail,pageable);
    }

    public List<Message> getAllMessage() {
        return messageRepository.findAll();
    }

    public List<Message> getDiscussion(String idUser1, String idUser2) {
        List<Message> fromOnetoTwo = messageRepository.findByReceiverEmailAndSenderId(idUser1, idUser2);
        List<Message> fromTwotoOne = messageRepository.findBySenderIdAndReceiverEmail(idUser2, idUser1);

        fromTwotoOne.addAll(fromOnetoTwo);

        return fromTwotoOne.stream().sorted(Comparator.comparing(Message::getDate)).toList();
    }



    public HashMap<String, Message> getContact(String idUser) {
        List<Message> messageList = this.getAllMessage();

        HashMap<String, Message> utilisateurAvecEchange = new HashMap<>();

        List<Message> messageUtilisateur = messageList.stream()
                .filter(message -> message.getSenderId().equals(idUser) || message.getReceiverEmail().equals(idUser))
                .toList();

        for (Message message : messageUtilisateur) {
            String autreUser = message.getSenderId().equals(idUser) ? message.getReceiverEmail()
                    : message.getSenderId();
            Message lastMessage = utilisateurAvecEchange.get(autreUser);
            if (lastMessage == null) {
                utilisateurAvecEchange.put(autreUser, new Message(message.getSenderId(), message.getSenderName(),
                        message.getReceiverEmail(), message.getContent(), message.getDate(),
                        message.getPicturePath()));
            } else {
                if (message.getDate().compareTo(lastMessage.getDate()) > 0) {
                    lastMessage.setContent(message.getContent());
                    lastMessage.setDate(message.getDate());
                }
            }
        }
        return this.triDateMessage(utilisateurAvecEchange);
    }

    public HashMap<String, Message> triDateMessage(HashMap<String, Message> map) {
        List<Map.Entry<String, Message>> entryList = new ArrayList<>(map.entrySet());

        Collections.sort(entryList,
                (entry1, entry2) -> entry2.getValue().getDate().compareTo(entry1.getValue().getDate()));

        HashMap<String, Message> sortedMessageMap = new LinkedHashMap<>();
        for (Map.Entry<String, Message> entry : entryList) {
            sortedMessageMap.put(entry.getKey(), entry.getValue());
        }
        return sortedMessageMap;
    }
}
