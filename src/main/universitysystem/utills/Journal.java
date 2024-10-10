package universitysystem.utills;

import universitysystem.entity.User;
import universitysystem.interfaces.Observer;
import universitysystem.interfaces.Subscription;
import universitysystem.research.ResearchPaper;

import java.io.Serializable;
import java.util.*;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

public class Journal implements Observer, Serializable {
    private static int idCounter = 0;
    private int releaseNumber;
    private String name;
    private HashSet<Subscription> subscribers;
    private LinkedHashSet<ResearchPaper> papers;

    {
        releaseNumber = idCounter++;
        subscribers = new HashSet<>();
        papers = new LinkedHashSet<>();
    }

    public Journal(){
        super();
    }

    public Journal(String name) {
        this.name = name;
    }

    public void notifyAllSubscribers(String text) {
    	for (Subscription subscriber : subscribers) {
            User user = (User) subscriber;
            user.addNotification(new Notification("Журнал " + name, text));
        }
    }

    public Integer getReleaseNumber() {
        return releaseNumber;
    }

    public boolean addNewPaper(ResearchPaper paper) {
        update("Добавлена новая статья");
        return papers.add(paper);
    }

    public boolean removePaper(ResearchPaper paper) {
        return papers.remove(paper);
    }

    public HashSet<Subscription> getSubscribers() {
        return subscribers;
    }

    public boolean addSubscriber(Subscription subscriber) {
        return subscribers.add(subscriber);
    }

    public boolean removeSubscriber(Subscription subscriber) {
        return subscribers.remove(subscriber);
    }

    public LinkedHashSet<ResearchPaper> getPapers() {
        return papers;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void update(String text) {
        notifyAllSubscribers(text);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Journal journal = (Journal) o;
        return Objects.equals(releaseNumber, journal.releaseNumber) &&
                Objects.equals(name, journal.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(releaseNumber, name, subscribers, papers);
    }

    @Override
    public String toString() {
        return "Journal: " + name + ", release number: " + releaseNumber
                + "\nPapers: " + papers.size() + ", subscribers: " + subscribers.size();
    }
}


