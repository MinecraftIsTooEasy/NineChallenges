# NineChallenges: SPD challenges

[DONE]

- [DONE] vanilla gui
- [DONE] Pharmacophobia
- [DONE] FaithIsMyArmor
- [DONE] OnDiet
- [DONE] ForbiddenRunes
- [DONE] BarrenLands
- [DONE] IntoDarkness
- [DONE] HostileChampions
    - Blazing (orange)
        - [DONE] +25% melee damage
        - [DONE] ignites target when attacks if not wet
        - [DONE] spreads fire on death if not wet
        - [DONE] immune to fire and lava damage
    - Projecting (purple)
        - [DONE] +25% melee damage or +50% ranged damage
        - [DONE] +50% attack reach in melee
        - [DONE] spawns only if it has AI
    - Antimagic (green)
        - [DONE] -50% damage taken
        - [DONE] don't get damaged by magic aspect
        - [DONE] spawns only if it can be damaged without magic aspect
    - Giant (blue)
        - [DONE] +100% hitbox and model, can spawn only if there's enough space
        - [DONE] -50% damage
        - [DONE] creeper's explosions are 2 times more powerful
        - [DONE] giants drop as many items as 4 regular mobs
    - Blessed (yellow)
        - [DONE] +300% regeneration amount
        - [DONE] when attacked by someone requires silver or magic aspects
        - [DONE] spawns only if it can be damaged by magic or silver aspects
    - Growing (red) - make IGrowingChampion which would have setters and getters for regen-speed, regen-amount, damage-multiplier, effective-health-multiplier
        - [DONE] +20% up to +300% damage
        - [DONE] +20% up to +300% effective HP (max hp and hp, so 12/20 with 45% increase is 17.4/29)
        - [DONE] +0.05% every tick it sees target aka +1% when sees
        - [DONE] +5% when attacks target
- [DONE] SwarmIntelligence


- [TODO]
- challenges (easiest to hardest in terms of implementing)
  - BadderBosses
    - Dragon
      - change stats: +50% health;
      - change worldgen: +100% crystals;
      - add crystal post-explosion event;
      - add dragon summoning mobs event;****

- add BGS gui
- maybe death from any champion must have custom "BTWCE:NM-like" death sentence
- add constant for configs and make challenge description dynamic (look into ShatteredBaubles EMI item info)

## Badder Bosses
`Bosses are much tougher with this challenge!`
- Dragon
    - dragon has +50% health
    - after first crystal destroyed, dragon turns random endermen into random hostile mobs every time it regenerates health from other crystals
    - +100% crystals generate
    - destroyed crystal charges up explosion that won't deal damage to dragon but would to other entities and blocks
- Wither
    - wither has +33% health
    - wither has +100% regeneration
    - wither can see through walls
    - blue wither skulls appear more often
## Hostile Champions
`You're not the only one who can level up!`
- hostile mobs could spawn with champion buff (only non-boss hostiles)
    - Blazing (orange)
        - +25% melee damage
        - ignites target when attacks if not wet
        - spreads fire on death if not wet
        - immune to fire and lava damage
    - Projecting (purple)
        - +25% damage when shooting
        - +100% attack reach in melee
    - Antimagic (green)
        - -50% damage taken
        - immune to enchants like sharpness, fire aspect, looting, etc.
    - Giant
        - +100% hitbox and model, can spawn only if there's enough space
        - -80% damage
    - Blessed (yellow)
        - +300% regeneration speed
        - when attacked in melee requires silver or magic aspects
    - Growing (red)
        - +20% regeneration speed
        - +20% regeneration amount
        - +20% damage
        - +20% effective HP (max hp and hp, so 12/20 with 45% increase is 17.4/29)
        - increases by 1% every 640 ticks it exists and every 80 ticks it has target
## On Diet
`Food's already scarce, but you have to watch your portions as well!`
- phytonutrient and protein values lowered to 1/3
- nutrition value lowered to 1/3 then floored
- satiation value lowered to 1/3 then floored
- if floored nutrition value is higher than lowered nutrition value, total satiation is increased by 1
- complex food gives slightly increased value of all its components
- player starts with 5 shanks
- each 5 levels give +1 shank up to +7 at level 35, in total 12/20 shanks
## Faith is My Armor
`Take on the world with nothing but some leather to protect you.`
- leather armor is unchanged
- armor with protection higher than leather's has lower protection
- Resistance effect gives less protection
## Pharmacophobia
`Imagine how awful is it to be allergic to averyilness cure. No need to imagine though.`
- for player Regeneration replaced by Poison
- for player Instant Health replaced by Instant Damage
## Barren Land
`There seems to be no healhy plants left in this accursed world...`
- crops won't grow
- trees won't grow
- flowers won't spawn naturally, but would from bone meal
- pumpkins won't spawn naturally
- leaves won't drop fruits
- grass won't drop seeds
- villagers won't sell plants
## Swarm Intelligence
`Watch out, monsters are getting smarter!`
- if mob sees player it tells other mobs location which they would start targeting
- targeted mobs won't know that their goal is player, they would just wander to target location
## Into Darkness
****
- 0 light is pitch black
- all moons are new moons, new moons produce 0 skylight
- minimal mob spawn light increased
## Forbidden Runes
`What a great world of magic! Huh? You forgot to learn the language?!`
- player can't use enchanting table
- player can use anvil to apply enchanting books + enchant golden apples with bottle of experience
- player can't read enchantments on already enchanted items
