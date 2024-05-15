package com.example.hala;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class DataManager {

    //public static string dataFilePath = "\\data\\books.xml";
    //public static
    public static List<FileUser> fileUsers = new ArrayList<>();
    public static List<FilePost> filePosts = new ArrayList<>();
    public static List<FileComment> fileComments = new ArrayList<>();
    public static List<FileLike> fileLikes = new ArrayList<>();
    public static List<FileFriend> fileFriends = new ArrayList<>();
    public static List<FileMessage> fileMessages = new ArrayList<>();

    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {

        //System.out.println(fileUsers.size());
        //loadUsersFile();
        //System.out.println(fileUsers.size());
        //System.out.println(fileUsers.size());
        //System.out.println( addNewUser("Hassan","12345") );
        //System.out.println(fileUsers.size());

        //updatePostCommentCounts("509130756","3");
        //updatePostLikesCounts("509130756","2");
        //DataManager.loadFriendsFile();
        //System.out.println(fileFriends.size());
        //loadPostLikes();
        loadUsersFile();
        System.out.println(getUserID("Han"));


        //System.out.println(updateFriendRecord("508204236","Hassan", "Jana Hassan" , "FR"));

    }

    public static void loadUsersFile() {

        try {
            fileUsers.clear();
        // Load the users data from the file
        File xmlFile = new File("\\data\\users.xml");
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document document = documentBuilder.parse(xmlFile);

        Element catalog = document.getDocumentElement();

        NodeList users = catalog.getChildNodes();

        for (int i = 0, ii = 0, n = users.getLength() ; i < n ; i++) {
            Node child = users.item(i);

            if (child.getNodeType() != Node.ELEMENT_NODE)
                continue;

            Element user = (Element)child;
            ii++;

            String id = user.getAttribute("id");
            String name = getCharacterData(findFirstNamedElement(child, "name"));
            String password = getCharacterData(findFirstNamedElement(child, "password"));
            String email = getCharacterData(findFirstNamedElement(child, "email"));
            String bio = getCharacterData(findFirstNamedElement(child, "bio"));
            String gender = getCharacterData(findFirstNamedElement(child, "gender"));

            // Build the fileUsers List
            FileUser userFile = new FileUser();
            userFile.setID(Integer.parseInt(id));
            userFile.setUsername(name);
            userFile.setPassword(password);
            userFile.setEmail(email);
            userFile.setBio(bio);
            userFile.setGender(gender);
            /*
            System.out.println(userFile.getId());
            System.out.println(userFile.getUsername());
            System.out.println(userFile.getPassword());
            System.out.println(userFile.getEmail());
            System.out.println(userFile.getGender());
            System.out.println(userFile.getBio());
            */
            fileUsers.add(userFile);
        }
    } catch (Exception e) {
        System.out.println("Error occured in main: " + e.getStackTrace());
    }
    }

    public static void loadFriendsFile() {

        try {
            fileFriends.clear();
            // Load the users data from the file
            File xmlFile = new File("\\data\\friends.xml");
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(xmlFile);

            Element friendsElement = document.getDocumentElement();

            NodeList friends = friendsElement.getChildNodes();

            for (int i = 0, ii = 0, n = friends.getLength() ; i < n ; i++) {
                Node child = friends.item(i);

                if (child.getNodeType() != Node.ELEMENT_NODE)
                    continue;

                Element friend = (Element)child;
                ii++;

                String id = friend.getAttribute("id");
                String userName = getCharacterData(findFirstNamedElement(child, "userName"));
                String friendName = getCharacterData(findFirstNamedElement(child, "friendName"));
                String status = getCharacterData(findFirstNamedElement(child, "status"));
                String creationDate = getCharacterData(findFirstNamedElement(child, "creationDate"));

                // Build the fileUsers List
                FileFriend fileFriend = new FileFriend();
                fileFriend.setId(Integer.parseInt(id));
                fileFriend.setUserName(userName);
                fileFriend.setFriendName(friendName);
                fileFriend.setStatus(status);
                fileFriend.setCreationDate(creationDate);

            /*
            System.out.println(userFile.getId());
            System.out.println(userFile.getUsername());
            System.out.println(userFile.getPassword());
            System.out.println(userFile.getEmail());
            System.out.println(userFile.getGender());
            System.out.println(userFile.getBio());
            */
                fileFriends.add(fileFriend);
            }
        } catch (Exception e) {
            System.out.println("Error occured in main: " + e.getStackTrace());
        }
    }

    public static String updateFriendStatus(String updateID, String updateStatus)
    {
        String result = "Failed";
        try {
            fileFriends.clear();
            // Load the users data from the file
            File xmlFile = new File("\\data\\friends.xml");
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(xmlFile);

            Element friendsElement = document.getDocumentElement();

            NodeList friends = friendsElement.getChildNodes();

            for (int i = 0, ii = 0, n = friends.getLength() ; i < n ; i++) {
                Node child = friends.item(i);

                if (child.getNodeType() != Node.ELEMENT_NODE)
                    continue;

                Element friend = (Element)child;
                ii++;

                String id = friend.getAttribute("id");

                if (updateID.equals(id )) {
                    findFirstNamedElement(child, "status").setTextContent(updateStatus);
                    break;
                }
            } // End Scanning Loop

            TransformerFactory tfact = TransformerFactory.newInstance();
            Transformer tform = tfact.newTransformer();
            tform.setOutputProperty(OutputKeys.INDENT, "no");
            tform.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "0");

            //tform.transform(new DOMSource(document), new StreamResult(System.out));
            tform.transform(new DOMSource(document), new StreamResult(new File("\\data\\friends.xml")));

            result = "Success";
        } catch (Exception e) {
            System.out.println("Error occured in main: " + e.getStackTrace());
        }
        return result;
    }

    public static String updateFriendRecord(String updateID, String updateUName,String updateFName, String updateStatus)
    {
        String result = "Failed";
        try {
            fileFriends.clear();
            // Load the users data from the file
            File xmlFile = new File("\\data\\friends.xml");
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(xmlFile);

            Element friendsElement = document.getDocumentElement();

            NodeList friends = friendsElement.getChildNodes();

            for (int i = 0, ii = 0, n = friends.getLength() ; i < n ; i++) {
                Node child = friends.item(i);

                if (child.getNodeType() != Node.ELEMENT_NODE)
                    continue;

                Element friend = (Element)child;
                ii++;

                String id = friend.getAttribute("id");

                if (updateID.equals(id )) {
                    findFirstNamedElement(child, "status").setTextContent(updateStatus);
                    findFirstNamedElement(child, "userName").setTextContent(updateUName);
                    findFirstNamedElement(child, "friendName").setTextContent(updateFName);
                    break;
                }
            } // End Scanning Loop

            TransformerFactory tfact = TransformerFactory.newInstance();
            Transformer tform = tfact.newTransformer();
            tform.setOutputProperty(OutputKeys.INDENT, "no");
            tform.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "0");

            //tform.transform(new DOMSource(document), new StreamResult(System.out));
            tform.transform(new DOMSource(document), new StreamResult(new File("\\data\\friends.xml")));

            result = "Success";
        } catch (Exception e) {
            System.out.println("Error occured in main: " + e.getStackTrace());
        }
        return result;
    }

    public static void loadMessagesFile() {

        try {
            fileMessages.clear();
            // Load the users messages from the file
            File xmlFile = new File("\\data\\messages.xml");
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(xmlFile);

            Element messagesElement = document.getDocumentElement();

            NodeList messages = messagesElement.getChildNodes();

            for (int i = 0, ii = 0, n = messages.getLength() ; i < n ; i++) {
                Node child = messages.item(i);

                if (child.getNodeType() != Node.ELEMENT_NODE)
                    continue;

                Element message = (Element)child;
                ii++;

                String id = message.getAttribute("id");
                String userName = getCharacterData(findFirstNamedElement(child, "userName"));
                String friendName = getCharacterData(findFirstNamedElement(child, "friendName"));
                String messageContent = getCharacterData(findFirstNamedElement(child, "message"));
                String creationDate = getCharacterData(findFirstNamedElement(child, "creationDate"));

                // Build the fileUsers List
                FileMessage fileMessage = new FileMessage();
                fileMessage.setId(Integer.parseInt(id));
                fileMessage.setUserName(userName);
                fileMessage.setFriendName(friendName);
                fileMessage.setMessage(messageContent);
                fileMessage.setCreationDate(creationDate);

                fileMessages.add(fileMessage);
            }
        } catch (Exception e) {
            System.out.println("Error occured in main: " + e.getStackTrace());
        }
    }

    public static String addNewMessage(FileMessage newMessage) {

        String result = "Failed";
        try {

                // Add the New Friend Record to the Friends File
                File xmlFile = new File("\\data\\messages.xml");
                DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
                Document document = documentBuilder.parse(xmlFile);

                Element messages = document.getDocumentElement();
                Element message = document.createElement("message");
                message.setAttribute("id", Integer.toString(newMessage.getId()));

                // Add the User Name
                Element elementName = document.createElement("userName");
                Text elementTtext = document.createTextNode(newMessage.getUserName());
                elementName.appendChild(elementTtext);
                message.appendChild(elementName);

                // Add the friendName
                elementName = document.createElement("friendName");
                elementTtext = document.createTextNode(newMessage.getFriendName());
                elementName.appendChild(elementTtext);
                message.appendChild(elementName);

                // Add the friendName
                elementName = document.createElement("message");
                elementTtext = document.createTextNode(newMessage.getMessage());
                elementName.appendChild(elementTtext);
                message.appendChild(elementName);

                // Add the creationDate
                elementName = document.createElement("creationDate");
                elementTtext = document.createTextNode(newMessage.getCreationDate());
                elementName.appendChild(elementTtext);
                message.appendChild(elementName);

                // Add the new User Node to the document
                messages.appendChild(message);

                TransformerFactory tfact = TransformerFactory.newInstance();
                Transformer tform = tfact.newTransformer();
                tform.setOutputProperty(OutputKeys.INDENT, "no");
                tform.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "0");

                //tform.transform(new DOMSource(document), new StreamResult(System.out));
                tform.transform(new DOMSource(document), new StreamResult(new File("\\data\\messages.xml")));

                result = "Success";

            } catch (Exception e) {
                System.out.println("Error occured in main: " + e.getStackTrace());
            }
        return result;
    }

    public static String addPostLike(FileLike newPostLike) {

        String result = "Failed";
        try {

            // Add the New Friend Record to the Friends File
            File xmlFile = new File("\\data\\likes.xml");
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(xmlFile);

            Element postlikes = document.getDocumentElement();
            Element postlike = document.createElement("postlike");
            postlike.setAttribute("id", Integer.toString(newPostLike.getId()));

            // Add the postID
            Element elementName = document.createElement("postID");
            Text elementTtext = document.createTextNode(Integer.toString(newPostLike.getPostID()));
            elementName.appendChild(elementTtext);
            postlike.appendChild(elementName);

            // Add the friendName
            elementName = document.createElement("createdBy");
            elementTtext = document.createTextNode(newPostLike.getCreatedBy());
            elementName.appendChild(elementTtext);
            postlike.appendChild(elementName);

            // Add the creationDate
            elementName = document.createElement("creationDate");
            elementTtext = document.createTextNode(newPostLike.getCreationDate());
            elementName.appendChild(elementTtext);
            postlike.appendChild(elementName);

            // Add the new Post Like  Node to the document
            postlikes.appendChild(postlike);

            TransformerFactory tfact = TransformerFactory.newInstance();
            Transformer tform = tfact.newTransformer();
            tform.setOutputProperty(OutputKeys.INDENT, "no");
            tform.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "0");

            //tform.transform(new DOMSource(document), new StreamResult(System.out));
            tform.transform(new DOMSource(document), new StreamResult(new File("\\data\\likes.xml")));

            result = "Success";

        } catch (Exception e) {
            System.out.println("Error occured in main: " + e.getStackTrace());
        }
        return result;
    }

    public static void loadPostLikes() {

        try {
            fileLikes.clear();
            // Load the users messages from the file
            File xmlFile = new File("\\data\\likes.xml");
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(xmlFile);

            Element postlikesElement = document.getDocumentElement();

            NodeList postlikes = postlikesElement.getChildNodes();

            for (int i = 0, ii = 0, n = postlikes.getLength() ; i < n ; i++) {
                Node child = postlikes.item(i);

                if (child.getNodeType() != Node.ELEMENT_NODE)
                    continue;

                Element postlike = (Element)child;
                ii++;

                String id = postlike.getAttribute("id");
                String postID = getCharacterData(findFirstNamedElement(child, "postID"));
                String createdBy = getCharacterData(findFirstNamedElement(child, "createdBy"));
                String creationDate = getCharacterData(findFirstNamedElement(child, "creationDate"));

                // Build the file Like List
                FileLike fileLike = new FileLike();
                fileLike.setId(Integer.parseInt(id));
                fileLike.setPostID(Integer.parseInt(postID));
                fileLike.setCreatedBy(createdBy);
                fileLike.setCreationDate(creationDate);

                fileLikes.add(fileLike);
            }
        } catch (Exception e) {
            System.out.println("Error occured in main: " + e.getStackTrace());
        }
    }

    public static void loadPostsFile() {

        try {
            filePosts.clear();
            // Load the users messages from the file
            File xmlFile = new File("\\data\\posts.xml");
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(xmlFile);

            Element postsElement = document.getDocumentElement();

            NodeList posts = postsElement.getChildNodes();

            for (int i = 0, ii = 0, n = posts.getLength() ; i < n ; i++) {
                Node child = posts.item(i);

                if (child.getNodeType() != Node.ELEMENT_NODE)
                    continue;

                Element post = (Element)child;
                ii++;

                String id = post.getAttribute("id");
                String author = getCharacterData(findFirstNamedElement(child, "author"));
                String content = getCharacterData(findFirstNamedElement(child, "content"));
                String likescount = getCharacterData(findFirstNamedElement(child, "likescount"));
                String commentsCount = getCharacterData(findFirstNamedElement(child, "commentsCount"));
                String creationDate = getCharacterData(findFirstNamedElement(child, "creationDate"));

                // Build the fileUsers List
                FilePost filePost = new FilePost();
                filePost.setId(Integer.parseInt(id));
                filePost.setAuthor(author);
                filePost.setContent(content);
                filePost.setLikesCount(Integer.parseInt(likescount));
                filePost.setCommentsCount(Integer.parseInt(commentsCount));
                filePost.setCreationDate(creationDate);

                filePosts.add(filePost);
            }
        } catch (Exception e) {
            System.out.println("Error occured in main: " + e.getStackTrace());
        }
    }

    public static void loadCommentsFile() {

        try {
            fileComments.clear();
            // Load the users messages from the file
            File xmlFile = new File("\\data\\comments.xml");
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(xmlFile);

            Element commentsElement = document.getDocumentElement();

            NodeList comments = commentsElement.getChildNodes();

            for (int i = 0, ii = 0, n = comments.getLength() ; i < n ; i++) {
                Node child = comments.item(i);

                if (child.getNodeType() != Node.ELEMENT_NODE)
                    continue;

                Element comment = (Element)child;
                ii++;

                String id = comment.getAttribute("id");
                String postID = getCharacterData(findFirstNamedElement(child, "postID"));
                String commentText = getCharacterData(findFirstNamedElement(child, "commenttext"));
                String createdBy = getCharacterData(findFirstNamedElement(child, "createdBy"));
                String postUserName = getCharacterData(findFirstNamedElement(child, "postUserName"));
                String creationDate = getCharacterData(findFirstNamedElement(child, "creationDate"));

                // Build the fileUsers List
                FileComment fileComment = new FileComment();
                fileComment.setId(Integer.parseInt(id));
                fileComment.setPostID(Integer.parseInt(postID));
                fileComment.setComment(commentText);
                fileComment.setCreatedBy(createdBy);
                fileComment.setPostUserName(postUserName);
                fileComment.setCreationDate(creationDate);

                fileComments.add(fileComment);
            }
        } catch (Exception e) {
            System.out.println("Error occured in main: " + e.getStackTrace());
        }
    }

    public static String addNewComment(FileComment newComment, int newPostCommentsCount) {

        String result = "Failed";
        try {

            // Add the New Comments Record to the Friends File
            File xmlFile = new File("\\data\\comments.xml");
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(xmlFile);

            Element comments = document.getDocumentElement();
            Element comment = document.createElement("comment");
            comment.setAttribute("id", Integer.toString(newComment.getId()));

            // Add the postID
            Element elementName = document.createElement("postID");
            Text elementTtext = document.createTextNode(Integer.toString(newComment.getPostID()));
            elementName.appendChild(elementTtext);
            comment.appendChild(elementName);

            // Add the Content
            elementName = document.createElement("commenttext");
            elementTtext = document.createTextNode(newComment.getComment());
            elementName.appendChild(elementTtext);
            comment.appendChild(elementName);

            // Add the Likes Count
            elementName = document.createElement("createdBy");
            elementTtext = document.createTextNode(newComment.getCreatedBy());
            elementName.appendChild(elementTtext);
            comment.appendChild(elementName);

            // Add the Comments Count
            elementName = document.createElement("postUserName");
            elementTtext = document.createTextNode(newComment.getPostUserName());
            elementName.appendChild(elementTtext);
            comment.appendChild(elementName);

            // Add the creationDate
            elementName = document.createElement("creationDate");
            elementTtext = document.createTextNode(newComment.getCreationDate());
            elementName.appendChild(elementTtext);
            comment.appendChild(elementName);

            // Add the new User Node to the document
            comments.appendChild(comment);

            TransformerFactory tfact = TransformerFactory.newInstance();
            Transformer tform = tfact.newTransformer();
            tform.setOutputProperty(OutputKeys.INDENT, "no");
            tform.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "0");

            //tform.transform(new DOMSource(document), new StreamResult(System.out));
            tform.transform(new DOMSource(document), new StreamResult(new File("\\data\\comments.xml")));

            result = "Success";
            // Update the Posts Comments Count
            updatePostCommentCounts( Integer.toString(newComment.getPostID()) ,Integer.toString(newPostCommentsCount));


        } catch (Exception e) {
            System.out.println("Error occured in main: " + e.getStackTrace());
        }
        return result;
    }

    public static String updatePostCommentCounts(String updateID, String postNewCount)
    {
        String result = "Failed";
        try {
            fileFriends.clear();
            // Load the users data from the file
            File xmlFile = new File("\\data\\posts.xml");
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(xmlFile);

            Element postsElement = document.getDocumentElement();

            NodeList posts = postsElement.getChildNodes();

            for (int i = 0, ii = 0, n = posts.getLength() ; i < n ; i++) {
                Node child = posts.item(i);

                if (child.getNodeType() != Node.ELEMENT_NODE)
                    continue;

                Element post = (Element)child;
                ii++;

                String id = post.getAttribute("id");

                if (updateID.equals(id )) {
                    findFirstNamedElement(child, "commentsCount").setTextContent(postNewCount);
                    break;
                }
            } // End Scanning Loop

            TransformerFactory tfact = TransformerFactory.newInstance();
            Transformer tform = tfact.newTransformer();
            tform.setOutputProperty(OutputKeys.INDENT, "no");
            tform.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "0");

            //tform.transform(new DOMSource(document), new StreamResult(System.out));
            tform.transform(new DOMSource(document), new StreamResult(new File("\\data\\posts.xml")));

            result = "Success";
        } catch (Exception e) {
            System.out.println("Error occured in main: " + e.getStackTrace());
        }
        return result;
    }

    public static String updatePostLikesCounts(String updateID, String postNewCount)
    {
        String result = "Failed";
        try {
            fileFriends.clear();
            // Load the users data from the file
            File xmlFile = new File("\\data\\posts.xml");
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(xmlFile);

            Element postsElement = document.getDocumentElement();

            NodeList posts = postsElement.getChildNodes();

            for (int i = 0, ii = 0, n = posts.getLength() ; i < n ; i++) {
                Node child = posts.item(i);

                if (child.getNodeType() != Node.ELEMENT_NODE)
                    continue;

                Element post = (Element)child;
                ii++;

                String id = post.getAttribute("id");

                if (updateID.equals(id )) {
                    findFirstNamedElement(child, "likescount").setTextContent(postNewCount);
                    break;
                }
            } // End Scanning Loop

            TransformerFactory tfact = TransformerFactory.newInstance();
            Transformer tform = tfact.newTransformer();
            tform.setOutputProperty(OutputKeys.INDENT, "no");
            tform.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "0");

            //tform.transform(new DOMSource(document), new StreamResult(System.out));
            tform.transform(new DOMSource(document), new StreamResult(new File("\\data\\posts.xml")));

            result = "Success";
        } catch (Exception e) {
            System.out.println("Error occured in main: " + e.getStackTrace());
        }
        return result;
    }

    public static String addNewPost(FilePost newPost) {

        String result = "Failed";
        try {

            // Add the New Friend Record to the Friends File
            File xmlFile = new File("\\data\\posts.xml");
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(xmlFile);

            Element posts = document.getDocumentElement();
            Element post = document.createElement("post");
            post.setAttribute("id", Integer.toString(newPost.getID()));

            // Add the User Name
            Element elementName = document.createElement("author");
            Text elementTtext = document.createTextNode(newPost.getAuthor());
            elementName.appendChild(elementTtext);
            post.appendChild(elementName);

            // Add the Content
            elementName = document.createElement("content");
            elementTtext = document.createTextNode(newPost.getContent());
            elementName.appendChild(elementTtext);
            post.appendChild(elementName);

            // Add the Likes Count
            elementName = document.createElement("likescount");
            elementTtext = document.createTextNode(Integer.toString(newPost.getLikesCount()));
            elementName.appendChild(elementTtext);
            post.appendChild(elementName);

            // Add the Comments Count
            elementName = document.createElement("commentsCount");
            elementTtext = document.createTextNode(Integer.toString(newPost.getCommentsCount()));
            elementName.appendChild(elementTtext);
            post.appendChild(elementName);

            // Add the creationDate
            elementName = document.createElement("creationDate");
            elementTtext = document.createTextNode(newPost.getCreationDate());
            elementName.appendChild(elementTtext);
            post.appendChild(elementName);

            // Add the new User Node to the document
            posts.appendChild(post);

            TransformerFactory tfact = TransformerFactory.newInstance();
            Transformer tform = tfact.newTransformer();
            tform.setOutputProperty(OutputKeys.INDENT, "no");
            tform.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "0");

            //tform.transform(new DOMSource(document), new StreamResult(System.out));
            tform.transform(new DOMSource(document), new StreamResult(new File("\\data\\posts.xml")));

            result = "Success";

        } catch (Exception e) {
            System.out.println("Error occured in main: " + e.getStackTrace());
        }
        return result;
    }

    public static String addNewUser(String username, String password) {

        String result = "User Already Exist";
        FileUser newUser = null;

        // Load a fresh copy of the users from file
        loadUsersFile();

        for (FileUser fileUser : fileUsers) {
            if (fileUser.getUsername().equals(username)) {
                newUser = fileUser;
                break;
            }
        }

        if (newUser == null) {
            try {
                // Add the new user to the fileUsers list
                newUser = new FileUser();
                //newUser.setID(fileUsers.size()+1);
                newUser.setUsername(username);
                newUser.setPassword(password);
                fileUsers.add(newUser);

                // Add the New User to the Users File
                File xmlFile = new File("\\data\\users.xml");
                DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
                Document document = documentBuilder.parse(xmlFile);

                Element users = document.getDocumentElement();
                Element user = document.createElement("user");
                user.setAttribute("id", Integer.toString(newUser.getId()));

                // Add the User
                Element elementName = document.createElement("name");
                Text elementTtext = document.createTextNode(username);
                elementName.appendChild(elementTtext);
                user.appendChild(elementName);

                // Add the User
                elementName = document.createElement("password");
                elementTtext = document.createTextNode(password);
                elementName.appendChild(elementTtext);
                user.appendChild(elementName);

                // Add the Password
                elementName = document.createElement("email");
                elementTtext = document.createTextNode("NA");
                elementName.appendChild(elementTtext);
                user.appendChild(elementName);

                // Add the Password
                elementName = document.createElement("bio");
                elementTtext = document.createTextNode("NA");
                elementName.appendChild(elementTtext);
                user.appendChild(elementName);

                // Add the Password
                elementName = document.createElement("gender");
                elementTtext = document.createTextNode("NA");
                elementName.appendChild(elementTtext);
                user.appendChild(elementName);
                // Add the new User Node to the document
                users.appendChild(user);

                TransformerFactory tfact = TransformerFactory.newInstance();
                Transformer tform = tfact.newTransformer();
                tform.setOutputProperty(OutputKeys.INDENT, "no");
                tform.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "0");

                //tform.transform(new DOMSource(document), new StreamResult(System.out));
                tform.transform(new DOMSource(document), new StreamResult(new File("\\data\\users.xml")));

                result = "Success";

            } catch (Exception e) {
                System.out.println("Error occured in main: " + e.getStackTrace());
            }
        }
        return result;
    }

    public static String updateFileUser(FileUser newFileUser)
    {
        String result = "Failed";
        try {
            // Load the users data from the file
            File xmlFile = new File("\\data\\users.xml");
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(xmlFile);

            Element usersElement = document.getDocumentElement();

            NodeList users = usersElement.getChildNodes();

            for (int i = 0, ii = 0, n = users.getLength() ; i < n ; i++) {
                Node child = users.item(i);

                if (child.getNodeType() != Node.ELEMENT_NODE)
                    continue;

                Element user = (Element)child;
                ii++;

                String id = user.getAttribute("id");

                if (String.valueOf(newFileUser.getId()).equals(id )) {
                    findFirstNamedElement(child, "password").setTextContent(newFileUser.getPassword());
                    findFirstNamedElement(child, "email").setTextContent(newFileUser.getEmail());
                    findFirstNamedElement(child, "gender").setTextContent(newFileUser.getGender());
                    findFirstNamedElement(child, "bio").setTextContent(newFileUser.getBio());
                    break;
                }
            } // End Scanning Loop

            TransformerFactory tfact = TransformerFactory.newInstance();
            Transformer tform = tfact.newTransformer();
            tform.setOutputProperty(OutputKeys.INDENT, "no");
            tform.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "0");

            //tform.transform(new DOMSource(document), new StreamResult(System.out));
            tform.transform(new DOMSource(document), new StreamResult(new File("\\data\\users.xml")));

            result = "Success";
        } catch (Exception e) {
            System.out.println("Error occured in main: " + e.getStackTrace());
        }
        return result;
    }

    public static String addNewFriend(FileFriend newFileFriend) {

        String result = "Failed";
        String userName = newFileFriend.getUserName();
        String friendName = newFileFriend.getFriendName();
        String status = newFileFriend.getStatus();
        String exitingID = "NA";
        // Check if there is an exiting Friend Record
        loadFriendsFile();
        for (int i = 0; i< fileFriends.size() ; i++) {
            FileFriend fileFriend = fileFriends.get(i);
            if (fileFriend.getUserName().equals(userName) && fileFriend.getFriendName().equals(friendName)) {
                exitingID = String.valueOf(fileFriend.getId());
                break;
            } else if (fileFriend.getUserName().equals(friendName) && fileFriend.getFriendName().equals(userName)) {
                exitingID = String.valueOf(fileFriend.getId());
                break;
            }
        }

        if (!exitingID.equals("NA")) {
            // Update the exiting file record
            result = updateFriendRecord(exitingID,userName, friendName,status);

        } else  // Add New Record
        {

            try {

                // Add the New Friend Record to the Friends File
                File xmlFile = new File("\\data\\friends.xml");
                DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
                Document document = documentBuilder.parse(xmlFile);

                Element friends = document.getDocumentElement();
                Element friend = document.createElement("friend");
                friend.setAttribute("id", Integer.toString(newFileFriend.getId()));

                // Add the User Name
                Element elementName = document.createElement("userName");
                Text elementTtext = document.createTextNode(newFileFriend.getUserName());
                elementName.appendChild(elementTtext);
                friend.appendChild(elementName);

                // Add the friendName
                elementName = document.createElement("friendName");
                elementTtext = document.createTextNode(newFileFriend.getFriendName());
                elementName.appendChild(elementTtext);
                friend.appendChild(elementName);

                // Add the status
                elementName = document.createElement("status");
                elementTtext = document.createTextNode(newFileFriend.getStatus());
                elementName.appendChild(elementTtext);
                friend.appendChild(elementName);

                // Add the creationDate
                elementName = document.createElement("creationDate");
                elementTtext = document.createTextNode(newFileFriend.getCreationDate());
                elementName.appendChild(elementTtext);
                friend.appendChild(elementName);

                // Add the new User Node to the document
                friends.appendChild(friend);

                TransformerFactory tfact = TransformerFactory.newInstance();
                Transformer tform = tfact.newTransformer();
                tform.setOutputProperty(OutputKeys.INDENT, "no");
                tform.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "0");

                //tform.transform(new DOMSource(document), new StreamResult(System.out));
                tform.transform(new DOMSource(document), new StreamResult(new File("\\data\\friends.xml")));

                result = "Success";

            } catch (Exception e) {
                System.out.println("Error occured in main: " + e.getStackTrace());
            }
        }
       return result;
    }

    public static int getUserID(String username) {
        int result = 0;
        FileUser user = null;

        for (FileUser fileUser : fileUsers) {
                if (fileUser.getUsername().equals(username)) {
                    result = fileUser.getId();
                    break;
                }
            }
        return result;
    }

    public static FileUser getUserRecord(String username) {
        FileUser result = null;

        for (FileUser fileUser : fileUsers) {
            if (fileUser.getUsername().equals(username)) {
                result = fileUser;
                break;
            }
        }
        return result;
    }

    public static String validateUser(String username, String password) {
        String result = "User not found";
        FileUser user = null;

        // Load a fresh copy of the users from file
        loadUsersFile();

        for (FileUser fileUser : fileUsers) {
            if (fileUser.getUsername().equals(username)) {
                user = fileUser;
                break;
            }
        }

        if (user != null)
        {
            if (password.equals(user.getPassword())) {
                result = "Success";
            } else result = "Invalid password";
        }
        return result;
    }

    public static boolean isFriend(String userName, String friendName) {
        boolean result = false;

        for (int i = 0; i < DataManager.fileFriends.size(); i++) {
            FileFriend loopFileF = DataManager.fileFriends.get(i);
            if (
                    (( loopFileF.getUserName().equals(userName)
                && loopFileF.getFriendName().equals(friendName) ) ||
                ( loopFileF.getUserName().equals(friendName) && loopFileF.getFriendName().equals(userName) ) )
                && ( loopFileF.getStatus().equals("F") || loopFileF.getStatus().equals("FR") )
            ) {
                result = true;
                break;
            }
        }
        return result;
    }

    private static String getCharacterData(Node parent)
    {
        StringBuilder text = new StringBuilder();

        if ( parent == null )
            return text.toString();

        NodeList children = parent.getChildNodes();

        for (int k = 0, kn = children.getLength() ; k < kn ; k++) {
            Node child = children.item(k);

            if (child.getNodeType() != Node.TEXT_NODE)
                break;

            text.append(child.getNodeValue());
        }

        return text.toString();
    }

    private static Node findFirstNamedElement(Node parent,String tagName)
    {
        NodeList children = parent.getChildNodes();

        for (int i = 0, in = children.getLength() ; i < in ; i++) {
            Node child = children.item(i);

            if (child.getNodeType() != Node.ELEMENT_NODE)
                continue;

            if (child.getNodeName().equals(tagName))
                return child;
        }

        return null;
    }

    public static List<FileLike> getPostLikes(int postID) {

        List<FileLike> postLikesList = new ArrayList<>();

        // Load a fresh copy of the users from file
        //loadUsersFile();
        for (int i = 0; i < fileLikes.size(); i++) {

            if ( postID == fileLikes.get(i).getPostID() )
                postLikesList.add(fileLikes.get(i));
        }
        return postLikesList;
    }

    public static boolean isPostLikedBefore(int postID, String userName) {

        boolean result = false;
        for (int i = 0; i < fileLikes.size(); i++) {
            FileLike loopFileLike = fileLikes.get(i);
            if ( (loopFileLike.getPostID() == postID ) && (loopFileLike.getCreatedBy().equals(userName)) ) {
                result = true;
                break;
            }
        }
        return result;
    }



}
