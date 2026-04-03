/*
Copyright Ann Barcomb and Khawla Shnaikat, 2024-2025
Licensed under GPL v3
See LICENSE.txt for more information.
*/

package edu.ucalgary.oop;

// I use enums here because I found it to be the easiest way to implement 'categories'
abstract class Skill {
    private enum skillCategory {
        MEDICAL,
        LANGUAGE,
        TRADE
    };
    private enum proficiencyLevel {
        BEGINNER,
        INTERMEDIATE,
        ADVANCED
    };
}
