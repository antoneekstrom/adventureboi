package items;

import data.NumberFactory;
import gamelogic.Item;

 /** <h4> To change when using this mold to create a new item:
 *   <p> - Give item a description.
 *   <p> - Give item effect in getEffect()
 *   <p> - Give item a stat in getStat()
 *   <p> - Setup scaleStats().
 */
public class StatupMold extends Item implements Statup {

	private static final long serialVersionUID = 1L;

    double STAT;

    /* Copy */

    public StatupMold() {
        super("name");
        description = description();
        effect = getEffect();
        addTag("tag");
        imageName(name()); /* Set a name for the image. */
    }

    @Override
    public String[] description() {
        return new String[] {""}; /* Set a description. */
    }

    @Override
    protected void scaleStats() {
        super.scaleStats();
        STAT *= NumberFactory.getStatScaling(level());
    }

	@Override
	public double getStat() { /* Set a stat. */
		return STAT;
	}

	@Override
	public String getEffect() { /* Set an effect. */
		return "" + (int)getStat() + "";
	}    

}