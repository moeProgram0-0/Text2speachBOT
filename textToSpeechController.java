package com.example.text2speachbot;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

import java.util.ArrayList;

public class textToSpeechController {
    private static VoiceManager voiceManager = VoiceManager.getInstance();

    public static ArrayList<String> getVoices() {
        System.setProperty("freetts.voices",
                "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");

        ArrayList<String> voices = new ArrayList<>();
        for (Voice voice : voiceManager.getVoices()) {
            voices.add(voice.getName());
        }
        return voices;
    }

    public static ArrayList<String> getSpeedRates() {
        ArrayList<String> speedRates = new ArrayList<>();
        speedRates.add("100"); // Normal
        speedRates.add("120"); // Little faster
        speedRates.add("140"); // Normal
        speedRates.add("170"); // Fast
        speedRates.add("200"); // Very Fast
        speedRates.add("60"); // Slow
        return speedRates;
    }

    public static ArrayList<String> getVolumeLevels() {
        ArrayList<String> volumeLevels = new ArrayList<>();
        for (int i = 0; i <= 10; i++) {
            volumeLevels.add(Integer.toString(i));
        }
        return volumeLevels;
    }

    public static void speak(String message, String voiceType, String rates, String volume) {
        try {
            Voice voice = voiceManager.getVoice(voiceType);
            if (voice == null) {
                throw new IllegalArgumentException("Cannot find voice: " + voiceType);
            }

            // Allocate the resources for the voice
            voice.allocate();
            // Set the speed at which the text will be spoken (words per minute)
            voice.setRate(Integer.parseInt(rates));
            // Set the volume (0-10)
            voice.setVolume(Integer.parseInt(volume));
            // Converts text to speech
            voice.speak(message);
            // Deallocate the resources when done
            voice.deallocate();
        } catch (Exception e) {
            System.err.println("Error in text-to-speech: " + e.getMessage());
        }
    }
}

//    Class textToSpeechController:
//    Private Static Variable voiceManager: VoiceManager = VoiceManager.getInstance()
//
//    Public Static Method getVoices() -> ArrayList<String>:
//        Set system property "freetts.voices" to "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory"
//        Create an empty ArrayList<String> called voices
//        For each Voice in voiceManager.getVoices():
//            Add voice.getName() to voices
//        Return voices
//
//    Public Static Method getSpeedRates() -> ArrayList<String>:
//        Create an empty ArrayList<String> called speedRates
//        Add "100" to speedRates
//        Add "120" to speedRates
//        Add "140" to speedRates
//        Add "170" to speedRates
//        Add "200" to speedRates
//        Add "60" to speedRates
//        Return speedRates
//
//    Public Static Method getVolumeLevels() -> ArrayList<String>:
//        Create an empty ArrayList<String> called volumeLevels
//        For integer i from 0 to 10:
//            Add Integer.toString(i) to volumeLevels
//        Return volumeLevels
//
//    Public Static Method speak(message: String, voiceType: String, rates: String, volume: String):
//        Try:
//            Set voice: Voice = voiceManager.getVoice(voiceType)
//            If voice is null:
//                Throw IllegalArgumentException("Cannot find voice: " + voiceType)
//            voice.allocate()
//            voice.setRate(Integer.parseInt(rates))
//            voice.setVolume(Integer.parseInt(volume))
//            voice.speak(message)
//            voice.deallocate()
//        Catch Exception e:
//            Print "Error in text-to-speech: " + e.getMessage() to standard error

