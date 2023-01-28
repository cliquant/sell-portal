[![Hits](https://hits.seeyoufarm.com/api/count/incr/badge.svg?url=https%3A%2F%2Fgithub.com%2Fcliquant%2Fsell-portal&count_bg=%2379C83D&title_bg=%23555555&icon=&icon_color=%23E7E7E7&title=hits&edge_flat=false)](https://hits.seeyoufarm.com)
![alts](https://i.imgur.com/u72wkSv.png)

https://user-images.githubusercontent.com/42009476/214687602-58b4bd75-3b70-4306-8902-ea91960d26d5.mp4

![alts](https://i.imgur.com/T4m74uw.png)

```
/sellportal - sellportal.command

/sellportal give <player name> (required to be online) - sellportal.give

/sellportal reload (config/portals/all) - sellportal.reload

Place Sell Portal - sellportal.place[/CODE]
```

![asdasd](https://i.imgur.com/4sdwU7n.png)


```
#sellportal by cliquant.



sell:

  prices:

    - "STONE:5.0"

    - "DIRT:5.0"

  pricesource: "shopguiplus" # shopguiplus | config | essentials.



portal:

  item:

    material: "head-eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvN2UyNDlhNDEwMDk0MTQ3NmRjYzcwNDY3NjBhNzY1ZWQ1N2JkZDY5N2ZmYzgyZjRlNWZjMTk5ZDdlOTExNDMwNiJ9fX0=" # if you want head then do head-value

    title: "&2&lSELL PORTAL"

    lore:

      - ""

      - "&7&o( Place it somewhere to generate Sell Portal. )"

      - ""

  hologram:

    hook: "decentholograms" # decentholograms or holographicdisplays

    lines:

      - "&2&lSELL PORTAL"

      - "&7&o( Churns items into {owner}&7&o's balance. )"

      - "&2Value sold: &f${totalsold} &7Sice Reboot"

  guis:

    rightclick:

      title: "&2SellPortal"

      size: 27 # 9, 18, 27, 36, 45 and 54

      filler: "BLACK_STAINED_GLASS_PANE" # Use "NONE" if you want empty.

      items: # ( remove-sellportal is required [don't rename the key] )

        main:

          title: "&2About"

          slot: 12

          material: "head-eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZmEyYWZhN2JiMDYzYWMxZmYzYmJlMDhkMmM1NThhN2RmMmUyYmFjZGYxNWRhYzJhNjQ2NjJkYzQwZjhmZGJhZCJ9fX0=" # if you want head then do head-value

          lore:

            - ""

            - "&f▪ Owner: &2{owner}"

            - "&f▪ Value sold: &2{totalsold}$ &7( Since Reboot )"

            - ""

        removeportal:

          title: "&cRemove Portal"

          slot: 14

          material: "head-eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTM5ZDEyYjQzYTJjOWZmZDdkZjg5ZWI2ZjlhMTA0YzYwYmI0NzQzZjU4YjFkNzJjODkxOTgwYzA5MDQ4NGYyNiJ9fX0=" # if you want head then do head-value

          lore:

            - ""

            - "&7( Click to remove portal )"

            - ""



messages:

  prefix: "&2SellPortal ▪"

  config-reloaded: "{prefix} &fSuccessfully reloaded config"

  portals-reloaded: "{prefix} &fSuccessfully reloaded portals"

  no-permission: "{prefix} &4Insufficient permission"

  dont-own-this: "{prefix} &4You don't own this portal"

  player-inventory-full: "{prefix} &4{player}'s &finventory is full"

  player-is-offline: "{prefix} &4{player} &fis offline"

  you-got-sellportal: "{prefix} &fYou got SellPortal"

  portal-mined: "{prefix} &fYou mined your sell portal &7&o( Its in your inventory )"

  gave-sellportal: "{prefix} &fSuccessfully gave SellPortal to {player}"

  placed-sellportal: "{prefix} &fSuccessfully placed"

  need-space: "{prefix} &fSellPortal need 5x5 area"

  take-distance: "{prefix} &fYou are placing Sell Portal too close to you, move a few blocks away!"



update-checker: true



version: 1.2 # dont touch.
```


![alts](https://i.imgur.com/1jtcBnm.png)

```
- ShopGuiPlus

- Vault

- HolographicDisplays

- DecentHolograms

- PlaceholderAPI

- Essentials



Dependencies:  HolographicDisplaysApi or DecentHolograms, Vault



If you don't see a plugin on the list and want compatibility added, contact me!
```


![alts](https://i.imgur.com/xR7WGZE.png)

https://discord.cliquant.lol
  
https://docs.cliquant.lol
