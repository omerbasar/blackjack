package ob.games.blackjack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static ob.games.blackjack.Card.ACE;

/**
 * @author obasar
 */
public class Hand {

   private Player owner;
   private int index;
   private int bet;
   private Card card1;
   private Card card2;
   private List<Card> extraCards;
   private BlackjackAction lastAction;

   public Hand(Player owner, int index, int bet, Card card1, Card card2) {
      this.owner = owner;
      this.index = index;
      this.bet = bet;
      this.card1 = card1;
      this.card2 = card2;
      this.lastAction = BlackjackAction.HIT;
      this.extraCards = new ArrayList<>();
   }

   public BlackjackAction getLastAction() {
      return lastAction;
   }

   public void setLastAction(BlackjackAction lastAction) {
      this.lastAction = lastAction;
   }

   public Card getCard1() {
      return card1;
   }

   public Card getCard2() {
      return card2;
   }

   public int getBet() {
      return bet;
   }

   public boolean isSame(){
      return card1.equals(card2);
   }

   public boolean isHard(){
      return !contains(ACE);
   }

   public boolean contains(Card card){
      return card1.equals(card) || card2.equals(card);
   }

   public Integer total(){
      int total = card1.getValue() + card2.getValue() + extraCards.stream().mapToInt(Card::getValue).sum();
      if(total > 21 && (contains(ACE) || extraCards.contains(ACE))){
         total -= 10;
      }
      return total;
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;

      Hand hand = (Hand) o;

      if (index != hand.index) return false;
      return owner.equals(hand.owner);
   }

   @Override
   public int hashCode() {
      int result = owner.hashCode();
      result = 31 * result + index;
      return result;
   }

   public void doubleBet() {
      owner.add(bet * -1);
      bet *= 2;
   }

   public void add(Card card) {
      extraCards.add(card);
   }

   public void setCard2(Card card2) {
      this.card2 = card2;
   }

   @Override
   public String toString() {
      return "Hand{" +
              "owner=" + owner +
              ", index=" + index +
              ", bet=" + bet +
              ", card1=" + card1 +
              ", card2=" + card2 +
              ", extraCards=" + extraCards +
              ", lastAction=" + lastAction +
              ", total=" + total() +
              '}';
   }
}
