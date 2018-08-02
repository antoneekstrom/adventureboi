package UI;

import java.io.File;
import java.util.Random;

import data.DataHandler;
import data.LevelData;
import data.LevelGenerator;
import data.RandomLevel;

public class RandomLevelUI extends GUI {

    public RandomLevelUI() {
        super("RandomLevel");
    }

    int randomLength = 3;

    LevelGenerator generator = new LevelGenerator();
    long seed = LevelGenerator.getRandomSeed();
    String levelName = "EpicLevel";

    String SEED_INPUT = "SeedInput", NAME_INPUT = "NameInput", LENGTH_INPUT = "LengthInput";

    @Override
    public void start() {
        setGuidelineSpacing(150);
        setGuidelineY1(350);

        //title
        addTitle("Generate a random level", -100);

        //back
        addBackButton();

        //seed inputfield
        addMenuButton(getGuidelineY1(), new UIButton(getName(), "Enter Seed", true){
            @Override
            public void useInput() {
                seed = Long.parseLong(getInput());
            }
            {
                takeInput(true);
                setInputPrefix("Seed");
                setTag(SEED_INPUT);
            }
        });

        //randomize a seed
        addObject(new UIButton(getName(), "Randomize", false) {
            @Override
            public void update() {
                super.update();
                UIObject p = getObjectsByTag(SEED_INPUT)[0];
                get().setLocation( (int) p.get().getMaxX() + 100, p.get().y);
            }
            {
                applyGeneralStyle(this);

                giveTask(new NavTask(){
                    @Override
                    public void run() {
                        randomizeSeed();
                    }
                });
            }
        });

        //name inputfield
        addMenuButton(getGuidelineY1(), new UIButton(getName(), "Enter Name", true) {
            @Override
            public void useInput() {
                levelName = getInput();
            }
            {
                takeInput(true);
                setInputPrefix("Name");
                setTag(NAME_INPUT);
            }
        });

        //randomize a name
        addObject(new UIButton(getName(), "Randomize", false) {
            @Override
            public void update() {
                super.update();
                UIObject p = getObjectsByTag(NAME_INPUT)[0];
                get().setLocation( (int) p.get().getMaxX() + 100, p.get().y);
            }
            {
                applyGeneralStyle(this);
                
                giveTask(new NavTask(){
                    @Override
                    public void run() {
                        randomizeName();
                    }
                });
            }
        });

        //level length
        addMenuButton(getGuidelineY1(), new UIButton(getName(), "Length:" + generator.length, true) {
            @Override
            public void leftMouseReleased() {
                super.leftMouseReleased();
                lengthDropdown(this);
            }
            {
                applyMenuStyle(this);
                setTag(LENGTH_INPUT);
            }
        });

        //generate new level
        addMenuButton("Generate Level", getGuidelineY1(), new NavTask(){
            @Override
            public void run() {
                generate();
            }
        });
    }

    void lengthDropdown(UIObject p) {
        addObject(new UIDropdown(p, lengthSet()));
    }

    TaskSet[] lengthSet() {
        return new TaskSet[] {
            new TaskSet("short", shortLength),
            new TaskSet("medium", mediumLength),
            new TaskSet("long", longLength),
        };
    }

    NavTask shortLength = new NavTask() {
        @Override
        public void run() {
            generator.length = RandomLevel.LENGTH_SHORT;
            updateLengthDisplay("short");
        }
    };
    NavTask mediumLength = new NavTask() {
        @Override
        public void run() {
            generator.length = RandomLevel.LENGH_MEDIUM;
            updateLengthDisplay("medium");
        }
    };
    NavTask longLength = new NavTask() {
        @Override
        public void run() {
            generator.length = RandomLevel.LENGH_LONG;
            updateLengthDisplay("long");
        }
    };

    void updateLengthDisplay(String length) {
        getObjectsByTag(LENGTH_INPUT)[0].setText("Length:" + length);
    }

    void randomizeSeed() {
        //randomize seed
        seed = LevelGenerator.getRandomSeed();

        //update button
        UIButton field = (UIButton) getObjectsByTag(SEED_INPUT)[0];
        field.setText(field.getInputPrefix() + ":" + seed);
    }

    void randomizeName() {
        //randomize name
        levelName = randomName(randomLength);

        //update button
        UIButton field = (UIButton) getObjectsByTag(NAME_INPUT)[0];
        field.setText(field.getInputPrefix() + ":" + levelName);
    }

    void generate() {
        LevelData levelData = generator.newLevel(seed, levelName);
        DataHandler.serialize(levelData, new File("data/levels/" + levelName + ".ser"));
    }

    char[] characters = new char[] {
        'a',
        'b',
        'x',
        'z',
        'y',
        'p',
        'f',
        'g',
        'o',
    };
    String[] words = new String[] {
        "nice",
        "level",
        "epic",
        "massive",
        "garbage",
        "not nice",
        "very",
    };
    String randomWord() {Random random = new Random();return words[random.nextInt(words.length)];}
    String randomName(int length) {
        String r = randomWord();
        String last = "";

        for (int i = 0; i < length - 1; i++) {
            String s = " " + randomWord();
            if (s.equals(last)) {
                s = " " + randomWord();
            }
            r += s;
            last = s;
        }

        return r;
    }

}