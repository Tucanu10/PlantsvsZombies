# 🧟 Plants vs. Zombies (NeoForge 1.21.1)

A modern recreation of *Plants vs. Zombies* as a Minecraft mod, built using the **NeoForge** engine. This mod features a custom resource system, intelligent plant AI, and unique combat mechanics.

---

## 🚀 Key Features

### 🌻 Sun Resource System
* **Data Persistence:** Sun balance is stored using NeoForge Data Attachments (saved to the player).
* **Real-time HUD:** A custom screen overlay shows your current Sun.
* **Syncing:** Custom network packets ensure the client and server always match.

### 🔫 Advanced Peashooter
* **Dual Modes:** Shift-Right-Click a Peashooter to toggle:
    * **Straight Mode:** Fixed cardinal rotation (Turret style).
    * **Free Range:** 360-degree AI targeting.

### 🛡️ Ownership & Security
* **Great for multiplayer:** Uses a UUID check. Ownership works on both official and offline-mode servers.
* **Placement Logic:** Plants automatically face the direction the player is looking when placed.

---

## ⌨️ Admin Commands

Use these commands for testing your setup:

* `/sun reset` — Resets your Sun counter to 0.
* `/sun set <amount>` — Sets your Sun to a specific value (Max 9990).

---


---

## 📜 Credits
Developed by **Tucanu**.
*Inspired by Plants vs. Zombies by PopCap Games.*