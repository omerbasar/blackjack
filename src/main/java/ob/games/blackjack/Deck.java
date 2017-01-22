package ob.games.blackjack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author obasar
 */
public class Deck {

   private int count;

   private List<Card> cards;

   public Deck(int count) {
      this.count = count;
      initCards(count);
   }

   private void initCards(int count) {
      cards = new ArrayList<>(count * 4 * 13);
      for(int i = 0 ; i < count * 4 ; i ++){
         Collections.addAll(cards, Card.values());
      }
      Collections.shuffle(cards);
   }

   public Card getCard(){
      if(cards.isEmpty()){
         initCards(count);
      }
      return cards.remove(0);
   }
}
