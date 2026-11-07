# NineChallenges: SPD challenges

DONE:

- vanilla gui
- Pharmacophobia
- FaithIsMyArmor

TODO:
- challenges (easiest to hardest in terms of implementing)
  - OnDiet - just the same as FaithIsyArmor, maybe;
  - ForbiddenRunes
    - change render of enchanting table, it must ignore pressing enchantment buttons + it must tell gibberish;
    - change applying books in anvils, it must output gibberish error;
    - change enchantments on items, it must say gibberish instead of their names;
  - IntoDarkness
    - change render of lightmap to be pitch black;
    - change moon to always be new moon;
    - change mob spawn rules to spawn in 0 light no matter how close player is;
  - SwarmIntelligence
    1) mobA starts targeting player ->
    2) mobA looks for other mobs ->
    3) other mobs start wandering to mobA's target location
    - all mobs do this 3 steps. it might be hard to do and to optimize
  - BadderBosses
    - Dragon
      - change stats: +50% health;
      - change worldgen: +100% crystals;
      - add crystal post-explosion event;
      - add dragon summoning mobs event;
  - HostileChampions
    - it requires adding new mob modifier like celestialType from HaTE + it requires coding a few champion logics, just takes lots of time
    - hostile champions must have charged creeper like glow with custom texture for each champion or must have colored particles
    - maybe death from any champion must have custom "BTWCE:NM-like" death sentence
- add BGS gui

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
- hostile mobs could spawn with champion buff
    - Blazing (orange)
        - +25% melee damage
        - ignites target when attacks
        - spreads fire on death
        - won't ignite or spread flame if wet
        - won't take damage from fire
    - Projecting (purple)
        - +25% projectile damage
        - +100% damage area
    - Antimagic (green)
        - -50% damage taken if weapon has magic aspect
        - immune to enchants
    - Giant
        - +100% hitbox and model
        - +300% heath
    - Blessed (yellow)
        - +300% regeneration speed
        - +300% regeneration amount
        - when attacked in melee requires silver or magic aspects
    - Growing (red)
        - +20% regeneration speed
        - +20% regeneration amount
        - +20% damage
        - +20% effective HP (max hp and hp, so 12/20 with 45% increase is 17.4/29)
        - increases by 1% every 600 ticks it exists and every 100 ticks it has target
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
`You can barely see past your nose.`
- 0 light is pitch black
- all moons are new moons, new moons produce 0 skylight
- minimal mob spawn light increased
## Forbidden Runes
`What a great world of magic! Huh? You forgot to learn the language?!`
- player can't use enchanting table
- player can't use anvil to apply enchanting books
- player can't read enchantments on already enchanted items


## Badder Bosses
## Hostile Champions
## On Diet
## Faith is My Armor
## Pharmacophobia
## Barren Land
## Swarm Intelligence
## Into Darkness
## Forbidden Runes
