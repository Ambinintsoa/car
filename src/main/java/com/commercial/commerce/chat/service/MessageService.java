package com.commercial.commerce.chat.service;

import com.websocket.chat.model.Message;
import com.websocket.chat.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.websocket.chat.model.Message;
import com.websocket.chat.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
@Service
public class MessageService {
    @Autowired
    private MessageRepository messageRepository;

    public void save(Message message){
        messageRepository.save(message);
    }

    public List<Message> getAllMessage(){
        return messageRepository.findAll();
    }

    public List<Message> getDiscussion(String idUser1, String idUser2){
        List<Message> fromOnetoTwo = messageRepository.findByIdSenderAndIdReceiver(idUser1,idUser2);
        List<Message> fromTwotoOne = messageRepository.findByIdReceiverAndIdSender(idUser2,idUser1);

        fromTwotoOne.addAll(fromOnetoTwo);

        return fromTwotoOne.stream().sorted(Comparator.comparing(Message::getDate)).toList();
    }

    public HashMap<String,Message> getContact(String idUser){
        List<Message> messageList = this.getAllMessage();

        HashMap<String, Message> utilisateurAvecEchange = new HashMap<>();

        List<Message> messageUtilisateur = messageList.stream()
                .filter(message -> message.getSender_id().equals(idUser) || message.getReceiver_id().equals(idUser))
                .toList();

        for(Message message : messageUtilisateur){
            String autreUser = message.getSender_id().equals(idUser) ? message.getReceiver_id() : message.getSender_id();
            Message lastMessage = utilisateurAvecEchange.get(autreUser);
            if(lastMessage == null){
                utilisateurAvecEchange.put(autreUser, new Message(message.getSender_id(),"Sender", message.getReceiver_id(),message.getContent(),message.getDate(),message.getPicturePath()));
            }else {
                if(message.getDate().compareTo(lastMessage.getDate()) > 0){
                    lastMessage.setContent(message.getContent());
                    lastMessage.setDate(message.getDate());
                }
            }
        }
        return this.triDateMessage(utilisateurAvecEchange);
    }

    public HashMap<String,Message> triDateMessage(HashMap<String,Message> map){
        List<Map.Entry<String, Message>> entryList = new ArrayList<>(map.entrySet());

        Collections.sort(entryList, (entry1, entry2) -> entry2.getValue().getDate().compareTo(entry1.getValue().getDate()));

        HashMap<String, Message> sortedMessageMap = new LinkedHashMap<>();
        for (Map.Entry<String, Message> entry : entryList) {
            sortedMessageMap.put(entry.getKey(), entry.getValue());
        }
        return sortedMessageMap;
    }
}
