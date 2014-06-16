package enchiridion.api;

import net.minecraft.util.ResourceLocation;

import org.apache.commons.lang3.Validate;

public class GuideImageLocation extends ResourceLocation {
	private final String resourceDomain;
    private final String resourcePath;

    public GuideImageLocation(String par1Str, String par2Str) {
    	super(par1Str, par2Str);
        Validate.notNull(par2Str);

        if (par1Str != null && par1Str.length() != 0) {
            resourceDomain = par1Str;
        } else {
            resourceDomain = "minecraft";
        }

        resourcePath = par2Str;
    }

    public GuideImageLocation(String par1Str) {
    	super(par1Str);
        String s1 = "minecraft";
        String s2 = par1Str;
        int i = par1Str.indexOf(58);

        if (i >= 0)
        {
            s2 = par1Str.substring(i + 1, par1Str.length());

            if (i > 1)
            {
                s1 = par1Str.substring(0, i);
            }
        }

        resourceDomain = s1.toLowerCase();
        resourcePath = s2;
    }

    public String getResourcePath() {
        return resourcePath;
    }

    public String getResourceDomain() {
        return resourceDomain;
    }

    public String toString() {
        return resourceDomain + ":" + this.resourcePath;
    }

    public boolean equals(Object par1Obj)
    {
        if (this == par1Obj) {
            return true;
        }
        else if (!(par1Obj instanceof ResourceLocation)) {
            return false;
        }
        else
        {
        	GuideImageLocation resourcelocation = (GuideImageLocation)par1Obj;
            return this.resourceDomain.equals(resourcelocation.resourceDomain) && this.resourcePath.equals(resourcelocation.resourcePath);
        }
    }

    public int hashCode() {
        return 31 * this.resourceDomain.hashCode() + this.resourcePath.hashCode();
    }
}
