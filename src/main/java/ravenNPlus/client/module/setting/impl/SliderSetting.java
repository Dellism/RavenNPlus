package ravenNPlus.client.module.setting.impl;

import com.google.gson.JsonObject;
import ravenNPlus.client.clickgui.RavenNPlus.Component;
import ravenNPlus.client.clickgui.RavenNPlus.components.ModuleComponent;
import ravenNPlus.client.module.setting.Setting;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class SliderSetting extends Setting {

   private final String name;
   private double value;
   private final double max;
   private final double min;
   private final double interval;
   private final double defaultVal;

   public SliderSetting(String name, double defaultValue, double minValue, double maxValue, double addValue) {
      super(name);
      this.name = name;
      this.value = defaultValue;
      this.min = minValue;
      this.max = maxValue;
      this.interval = addValue;
      this.defaultVal = defaultValue;
   }

   public String getName() { return this.name; }
   @Override
   public void resetToDefaults() { this.value = defaultVal; }

   @Override
   public JsonObject getConfigAsJson() {
      JsonObject data = new JsonObject();
      data.addProperty("type", getSettingType());
      data.addProperty("value", getValue());
      return data;
   }

   @Override
   public String getSettingType() {
      return "slider";
   }

   @Override
   public void applyConfigFromJson(JsonObject data) {
      if(!data.get("type").getAsString().equals(getSettingType()))
         return;

      setValue(data.get("value").getAsDouble());
   }

   @Override
   public Component createComponent(ModuleComponent moduleComponent) { return null; }

   public double getValue() { return r(this.value, 2); }
   public double  getMin()  { return this.min; }
   public double  getMax()  { return this.max; }

   public void setValue(double n) {
      n = check(n, this.min, this.max);
      n = (double)Math.round(n * (1.0D / this.interval)) / (1.0D / this.interval);
      this.value = n;
   }

   public static double check(double v, double i, double a) {
      v = Math.max(i, v);
      v = Math.min(a, v);
      return v;
   }

   public double roundValue() {
      return Math.round(this.getValue());
   }


   public static double r(double v, int p) {
      if (p < 0) {
         return 0.0D;
      } else {
         BigDecimal bd = new BigDecimal(v);
         bd = bd.setScale(p, RoundingMode.HALF_UP);

         return bd.doubleValue();
      }
   }
}