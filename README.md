# MacePowers Plugin

**Version:** 1.0.4 (native for paper-1.21) <br/>
**Author:** Jake Shamp  
**Description:** Adds maces with custom powers, unlockable through stages. Tracks kill counts and includes cooldown and stage progression features.

---

## ⚙️ Commands

### `/givemace <item>`
Gives the specified custom mace to the player.  
**Items:** `starwrought`, `arachnidstreasure`, `kingsmace`, `godmace`  
**Permission:** `macepowers.givemace`

---

### `/stage <stageNumber>`
Initiates a stage, broadcasts a message, and unlocks a crafting recipe.  
**Stages:** `1`, `2`, `3`, `4`  
**Special Options:** `start`, `end`, `reset-recipes`  
**Permission:** `macepowers.stage`

---

### `/cooldown`
Resets all your cooldowns (ability, dash, GUI).  
**Permission:** `macepowers.cooldown`

---

### `/killcount [player|mace] <name>`
Displays total or specific kill counts for players or maces.  

Examples:
- `/killcount` – Global total  
- `/killcount player Jake`  
- `/killcount player Jake godmace`  
- `/killcount mace starwrought`  

**Permission:** `macepowers.killcount`

---

## 🔐 Permissions

| Permission                | Description                         | Default |
|--------------------------|-------------------------------------|---------|
| `macepowers.givemace`    | Allows use of `/givemace`           | `op`    |
| `macepowers.stage`       | Allows use of `/stage`              | `op`    |
| `macepowers.cooldown`    | Allows use of `/cooldown`           | `op`    |
| `macepowers.killcount`   | Allows use of `/killcount`          | `true`  |
