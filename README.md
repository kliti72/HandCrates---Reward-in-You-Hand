# Hand Crates

Turn any block into a customizable physical crate for your Hytale server — create, fill, and hand out reward crates entirely in-game.

![Crate banner](IMAGE_PLACEHOLDER_BANNER.png)

## Features

- Create a crate from any in-game block type
- In-game GUI editor to fill crate rewards (drag & drop items)
- Custom display name and lore with color tag support
- Give crates directly to players via command
- List, edit, delete crates anytime
- Hot reload — no server restart needed

## Commands & Examples

| Command | Example |
|---|---|
| Create a crate | `/handcrates --create=Winter --type=Furniture_Lumberjack_Chest_Small` |
| Set display name | `/handcrates --create=Winter --display=<green><bold>Winter Crate</bold></green>` |
| Set lore | `/handcrates --edit=Winter --lore="Open with care..."` |
| Edit rewards (opens GUI) | `/handcrates --edit=Winter` |
| Give to a player | `/handcrates --give=Winter --player=Steve` |
| List all crates | `/handcrates --list=true` |
| Delete a crate | `/handcrates --delete=Winter` |
| Reload plugin | `/handcrates --reload=true` |

![Reward editor GUI](IMAGE_PLACEHOLDER_GUI.png)

## Installation

1. Drop the `.jar` into your server's `mods/` folder
2. Restart the server
3. Run `/handcrates --list=true` to confirm it's loaded

## Requirements

Hytale server with operator/admin permissions to manage commands.

---

*Feedback and feature requests welcome via the project page.*