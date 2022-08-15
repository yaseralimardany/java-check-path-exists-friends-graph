import java.util.Collection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class Friend {
    private Collection<Friend> friends;
    private String email;

    public Friend(String email) {
        this.email = email;
        this.friends = new ArrayList<Friend>();
    }

    public String getEmail() {
        return email;
    }

    public Collection<Friend> getFriends() {
        return friends;
    }

    public void addFriendship(Friend friend) {
        friends.add(friend);
        friend.getFriends().add(this);
    }

    public boolean canBeConnected(Friend friend) {
        if (friend.getFriends().size() == 0) {
            return false;
        }
        HashMap<String, Integer> visitedFriendsMap = new HashMap<String, Integer>();
        ArrayList<Friend> visitedFriendsList = new ArrayList<Friend>();

        visitedFriendsMap.put(this.getEmail(), 0);
        visitedFriendsList.add(this);

        Iterator<Friend> bfsChildren = getFriends().iterator();
        while (bfsChildren.hasNext()) {
            Friend bfsChild = (Friend) bfsChildren.next();
            // Add to queue (First bfs path)
            visitedFriendsList.add(bfsChild);
            visitedFriendsMap.put(bfsChild.getEmail(), 0);
        }

        while (visitedFriendsList.size() > 0) {
            Friend childVisitedFriend = visitedFriendsList.remove(0);
            if (childVisitedFriend.getEmail().equals(friend.getEmail())) {
                return true;
            }
            bfsChildren = childVisitedFriend.getFriends().iterator();
            while (bfsChildren.hasNext()) {
                Friend bfsChild = (Friend) bfsChildren.next();
                if (!visitedFriendsMap.containsKey(bfsChild.getEmail())) {
                    // Add to queue
                    visitedFriendsList.add(bfsChild);
                    visitedFriendsMap.put(bfsChild.getEmail(), 0);
                }
            }
        }
        return false;
    }

    public static void main(String[] args) {
        Friend a = new Friend("A");
        Friend b = new Friend("B");
        Friend c = new Friend("C");

        a.addFriendship(b);
        b.addFriendship(c);

        System.out.println(a.canBeConnected(c));
    }
}