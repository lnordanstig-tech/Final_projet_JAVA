# Final_projet_JAVA

Game project in the course "JAVA Programming" (JAV)

This game is a Harry Potter-themed version of Hay Day. Here is some trivia about Harry Potter and the items available in the game:

Animals:
- Ashwinder = A magical serpent that was created from the remains of any magical fire that was allowed to burn unchecked.
- Hippogriff = A magical beast that has the front legs, wings, and head of a giant eagle and the body, hind legs, and tail of a horse. 
- Big-horn-sheep = A magical species of sheep. It has a body covered in wool and is distinguished by an enormous single horn in the shape of a plate.
  
Products:
- Gillyweed = A magical plant that, when eaten, allows a human to breathe underwater. When eaten, it gives the consumer gills, allowing them to breathe underwater, and webbing between the fingers and toes, allowing them to swim underwater with ease.
- Pumpkin = The same as in our world.
- Carrot = Just ordinary carrots. They have to eat at Hogwarts too.
- Mandrake = Mandrake, or Mandragora, is used to return people who have been transfigured or cursed to their original state. It is also, however, dangerous, since the cry of the Mandrake is fatal to anyone who hears it. 
- Ashwinder-egg = An egg from an ashwinder. 
- Never-Tangle-Wool = Wool from the big-horn-sheep.
- Milk = Milk from a hippogriff. 

The goal of the game is getting 1000 gold within 60 days.

You start with 100 gold, six empty plots, one ashwinder, 10 feed portions, and seeds:
two mandrake, two carrot, one pumpkin, and one gillyweed. There are six animal spaces.

## Run the game

Requires **JDK 17 or newer**. No external libraries are needed.

Start with writing git clone https://github.com/lnordanstig-tech/Final_projet_JAVA.git in your terminal. 

**macOS / Linux:** Run these commands from the folder containing `src`:

```sh
mkdir -p out
javac --release 17 -d out $(find src -name '*.java')
java -cp out farm.Main
```

**Windows PowerShell:** From the same folder:

```powershell
New-Item -ItemType Directory -Force out | Out-Null
Get-ChildItem src -Recurse -Filter *.java |
    ForEach-Object { (Resolve-Path -Relative $_.FullName).Replace('\', '/') } |
    Set-Content -Encoding ascii sources.txt
javac --release 17 -d out '@sources.txt'
java -cp out farm.Main
```

## Commands

Commands and item names ignore letter case. Plots are numbered 1–6. Quantities must
be positive whole numbers; omitting a quantity means one item.

| Command | Effect |
| --- | --- |
| `status` | Show gold, day, plots, animals, and inventory. |
| `plant <plot> <crop>` | Plant one seed, for example `plant 1 mandrake`. |
| `water <plot>` | Water a crop for the current day. |
| `harvest <plot>` | Collect a mature crop and free its plot. |
| `harvest all` | Collect every ready crop and animal product. |
| `buy <item> [quantity]` | Buy seeds, feed, or animals, for example `buy feed 5`. |
| `sell <item> [quantity]` | Sell inventory items, for example `sell ashwinder-egg 2`. |
| `feed` | Feed every animal that has not been fed today. |
| `next` | End the current day and update growth and production. |
| `help` | Show instructions and shop prices. |
| `quit` | End the game. Progress is not saved. |

Only `next` advances time. Water crops and feed animals before using it; harvest
and sell when products are ready. Victory is checked when a day ends. Reach the
gold goal before your final `next`, which ends day 60.

## Crops and animals

Crops grow only on watered days. Watering is free and must be repeated each day.
Harvesting removes the crop, so planting again requires another seed.
Buy seeds using `<crop>-seed`, for example `buy gillyweed-seed 1`.

| Crop | Seed price | Normal watered days | Yield | Sale price each | Preferred season |
| --- | ---: | ---: | ---: | ---: | --- |
| `mandrake` | 8 | 3 | 2 | 16 | Spring |
| `carrot` | 12 | 4 | 2 | 24 | Autumn |
| `pumpkin` | 20 | 6 | 1 | 65 | Autumn |
| `gillyweed` | 30 | 10 | 2 | 100 | Summer |

Growth is twice as fast during the preferred season. Spring is days 1–15, summer
16–30, autumn 31–45, and winter 46–60. For example, gillyweed needs five watered
summer days instead of ten normal watered days.

Purchased animals go directly into the animal spaces. Each needs one feed portion
per day; feed costs 2 gold per portion. Unfed animals stop producing, become
hungrier, and lose happiness. Feeding resets hunger and increases happiness.
Animals hold one product at a time: harvest it to allow production to continue.

| Animal | Purchase price | Fed days per product | Product | Sale price each |
| --- | ---: | ---: | --- | ---: |
| `ashwinder` | 60 | 1 | `ashwinder-egg` | 12 |
| `hippogriff` | 180 | 2 | `milk` | 30 |
| `big-horn-sheep` | 150 | 3 | `never-tangle-wool` | 48 |

## Example opening

Enter these commands in a new game:

```text
plant 1 mandrake
water 1
feed
next
harvest all
sell ashwinder-egg
water 1
feed
next
harvest all
sell mandrake 2
sell ashwinder-egg
status
```

After two completed days, you have 156 gold. Keep growing crops, collecting
products, and reinvesting in animals, seeds, and feed to reach the goal.
