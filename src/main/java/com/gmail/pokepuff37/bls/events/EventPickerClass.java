package com.gmail.pokepuff37.bls.events;


import static com.gmail.pokepuff37.bls.events.events.AquaticPlayersClass.AquaticPlayers;
import static com.gmail.pokepuff37.bls.events.events.BlankRoundClass.BlankRound;
import static com.gmail.pokepuff37.bls.events.events.BlockSwapClass.BlockSwap;
import static com.gmail.pokepuff37.bls.events.events.BuriedTreasureClass.BuriedTreasure;
import static com.gmail.pokepuff37.bls.events.events.CraftingRecipeStopClass.CraftingRecipeStop;
import static com.gmail.pokepuff37.bls.events.events.CrowningClass.Crowning;
import static com.gmail.pokepuff37.bls.events.events.DarkAgesClass.DarkAges;
import static com.gmail.pokepuff37.bls.events.events.FortniteClass.Fortnite;
import static com.gmail.pokepuff37.bls.events.events.GiveExpClass.GiveExp;
import static com.gmail.pokepuff37.bls.events.events.IShowSpeed100Class.IShowSpeed100;
import static com.gmail.pokepuff37.bls.events.events.LiquidSwapClass.LiquidSwap;
import static com.gmail.pokepuff37.bls.events.events.NukeClass.Nuke;
import static com.gmail.pokepuff37.bls.events.events.RandomEffectsClass.RandomEffects;
import static com.gmail.pokepuff37.bls.events.events.RandomItemsClass.RandomItems;
import static com.gmail.pokepuff37.bls.events.events.RicksRollsClass.RicksRolls;
import static com.gmail.pokepuff37.bls.events.events.ScavengerHuntClass.ScavengerHunt;
import static com.gmail.pokepuff37.bls.events.events.SizeChangeClass.SizeChange;
import static com.gmail.pokepuff37.bls.events.events.SpawnMobsClass.SpawnMobs;
import static com.gmail.pokepuff37.bls.events.events.StructurePlaceClass.StructurePlace;
import static com.gmail.pokepuff37.bls.events.events.ThanosClass.Thanos;
import static com.gmail.pokepuff37.bls.events.events.WallsGaloreClass.WallsGalore;
import static com.gmail.pokepuff37.bls.events.events.WindWakerDownClass.WindWakerDown;
import static com.gmail.pokepuff37.bls.events.events.WindWakerUpClass.WindWakerUp;
import static com.gmail.pokepuff37.bls.events.events.WorldborderGrowClass.WorldborderGrow;
import static com.gmail.pokepuff37.bls.events.events.WorldborderMoveClass.WorldborderMove;
import static com.gmail.pokepuff37.bls.events.events.WorldborderShrinkClass.WorldborderShrink;
import static com.gmail.pokepuff37.bls.events.events.ZombifiedClass.Zombified;

public class EventPickerClass {
    public static int EventPicker = -1;
    public static void EventPickerMethod() {
        if (EventPicker == -1) {
            EventPicker = (int)(Math.random() * 27);
        }
        System.out.println(EventPicker);

        if (EventPicker == 0) {
            boolean fish = true;
            AquaticPlayers();
        } else if (EventPicker == 1) {
            BlankRound();
        } else if (EventPicker == 2) {
            BlockSwap();
        } else if (EventPicker == 3) {
            BuriedTreasure();
        } else if (EventPicker == 4) {
            CraftingRecipeStop();
        } else if (EventPicker == 5) {
            Crowning();
        } else if (EventPicker == 6) {
            DarkAges();
        } else if (EventPicker == 7) {
            Fortnite();
        } else if (EventPicker == 8) {
            GiveExp();
        } else if (EventPicker == 9) {
            IShowSpeed100();
        } else if (EventPicker == 10) {
            LiquidSwap();
        } else if (EventPicker == 11) {
            Nuke();
        } else if (EventPicker == 12) {
            RandomEffects();
        } else if (EventPicker == 13) {
            RandomItems();
        } else if (EventPicker == 14) {
            RicksRolls();
        } else if (EventPicker == 15) {
            ScavengerHunt();
        } else if (EventPicker == 16) {
            SizeChange();
        } else if (EventPicker == 17) {
            SpawnMobs();
        } else if (EventPicker == 18) {
            StructurePlace();
        } else if (EventPicker == 19) {
            Thanos();
        } else if (EventPicker == 20) {
            WallsGalore();
        } else if (EventPicker == 21) {
            WindWakerDown();
        } else if (EventPicker == 22) {
            WindWakerUp();
        } else if (EventPicker == 23) {
            WorldborderGrow();
        } else if (EventPicker == 24) {
            WorldborderMove();
        } else if (EventPicker == 25) {
            WorldborderShrink();
        } else if (EventPicker == 26) {
            Zombified();
//        } else if (EventPicker == 27) {
//        } else if (EventPicker == 28) {
//        } else if (EventPicker == 29) {
//        } else if (EventPicker == 30) {
//        } else if (EventPicker == 31) {
//        } else if (EventPicker == 32) {
//        } else if (EventPicker == 33) {
//        } else if (EventPicker == 34) {
//        } else if (EventPicker == 35) {
//        } else if (EventPicker == 36) {
//        } else if (EventPicker == 37) {
//        } else if (EventPicker == 38) {
//        } else if (EventPicker == 39) {
//        } else if (EventPicker == 40) {
        }
    }
}