package ob.games.blackjack;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author obasar
 */
public class Game {

   private Integer iteration;
   private Deck deck;
   private List<Player> players;
   private Hand dealerHand;

   public Game(int deckCount, int iteration, String... names) {
      this.iteration = iteration;
      players = new ArrayList<>(names.length);
      for (String name : names) {
         players.add(new Player(name, 100));
      }

      this.deck = new Deck(deckCount);
   }

   private void start() {
      for(int i = 0 ; i < iteration ; i ++){
         System.out.println("-----------------------------");
         System.out.println("------------- ITERATION : " + (i + 1));
         System.out.println("-----------------------------");
         play();
      }
   }

   private void play(){
      deal();
      print();
      for (Player player : players) {
         while(!player.isAllHandsStand()){
            for (int i = 0 ; i < player.getHands().size() ; i++) {
               Hand hand = player.getHands().get(i);
               if(hand.getLastAction().equals(BlackjackAction.STAND)){
                  continue;
               }
               BlackjackAction action = ActionSelector.select(hand, dealerHand.getCard1());
               System.out.println("..." + player + " takes action: " + action);
               switch (action){
                  case DOUBLE:
                     hand.doubleBet();
                     hand.add(deck.getCard());
                     action = BlackjackAction.STAND;
                     break;
                  case HIT:
                     hand.add(deck.getCard());
                     break;
                  case SPLIT:
                     player.addHand(hand.getCard2(), deck.getCard());
                     hand.setCard2(deck.getCard());
                     break;
                  case STAND:
                  default:
               }
               hand.setLastAction(action);
               System.out.println(hand);
            }
         }
      }

      while(dealerHand.total() < 17){
         System.out.println("...dealer gets card.");
         dealerHand.add(deck.getCard());
         System.out.println(dealerHand);
      }

      for (Player player : players) {
         for (Hand hand : player.getHands()) {
            HandResult result;
            if(hand.total() > 21){
               result = HandResult.LOSE;
            }else if(hand.getCard1().getValue() + hand.getCard2().getValue() == 21){
               result = HandResult.DOUBLE_WIN;
            }else if(dealerHand.total() > 21){
               result = HandResult.WIN;
            }else if(hand.total() > dealerHand.total()){
               result = HandResult.WIN;
            }else if(Objects.equals(hand.total(), dealerHand.total())){
               result = HandResult.DRAW;
            }else{
               result = HandResult.LOSE;
            }
            System.out.println("[" + result + "] " + hand);
            player.updateMoney(hand, result);
         }
         player.clearHands();
      }
      
   }

   public void print(){
      System.out.println(dealerHand);
      for (Player player : players) {
         for (Hand hand : player.getHands()) {
            System.out.println(hand);
         }
      }
   }

   private void deal() {
      System.out.println("...dealing cards.");
      for (Player player : players) {
         player.addHand(deck.getCard(), deck.getCard());
      }
      dealerHand = new Hand(Player.DEALER, 0, 0, deck.getCard(), deck.getCard());
   }

   private int getTotalPlayerAmount() {
      return players.stream().mapToInt(Player::getMoney).sum();
   }

   public static void main(String[] args) {
      DescriptiveStatistics stats = new DescriptiveStatistics();
      int gameCount = 100;
      int iteration = 1000;
      int deckCount = 6;
      String[] players = new String[]{"omer", "cihan"};

      for(int i = 0 ; i < gameCount ; i ++){
         Game game = new Game(deckCount, iteration, players);
         game.start();
         stats.addValue(game.getTotalPlayerAmount());
      }

      System.out.println(stats);
   }
}
