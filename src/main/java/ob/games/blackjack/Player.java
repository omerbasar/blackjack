package ob.games.blackjack;

import java.util.ArrayList;
import java.util.List;

/**
 * @author obasar
 */
public class Player {

   public static final Player DEALER = new Player("Dealer", 10000);

   private int betAmount = 1;
   private String name;
   private int money;
   private List<Hand> hands;

   public Player(String name, int money) {
      this.name = name;
      this.money = money;
      this.hands = new ArrayList<>();
   }

   public String getName() {
      return name;
   }

   public void add(int amount){
      money += amount;
   }

   public void addHand(Card card1, Card card2) {
      hands.add(new Hand(this, hands.size(), betAmount, card1, card2));
      add(betAmount * -1);
   }

   public List<Hand> getHands() {
      return hands;
   }

   public int getMoney() {
      return money;
   }

   public boolean isAllHandsStand() {
      return hands
              .stream()
              .filter(hand -> !hand.getLastAction().equals(BlackjackAction.STAND))
              .count() == 0;
   }

   public void updateMoney(Hand hand, HandResult result) {
      switch (result){
         case DOUBLE_WIN:
            add(hand.getBet() * 3);
            break;
         case WIN:
            add(hand.getBet() * 2);
            break;
         case DRAW:
            add(hand.getBet());
            break;
         case LOSE:
         default:
      }
   }

   public void clearHands() {
      hands.clear();
   }

   @Override
   public String toString() {
      return name + "(" + money + ")";
   }
}
