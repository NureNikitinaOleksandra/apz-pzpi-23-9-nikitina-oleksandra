package apzpract1;

public class examples {
    public static void main(String[] args) {
        
        System.out.println("===== 1. Builder З Director (Pizza) =====");
        Cook cook = new Cook(); // Директор

        // Готуємо Маргариту
        PizzaBuilder margaritaBuilder = new MargaritaBuilder();
        cook.makePizza(margaritaBuilder); // Директор керує
        Pizza pizza1 = margaritaBuilder.getPizza(); // Отримуємо результат
        System.out.println("Готова: " + pizza1);

        // Готуємо Пепероні
        PizzaBuilder pepperoniBuilder = new PepperoniBuilder();
        cook.makePizza(pepperoniBuilder); // Директор керує
        Pizza pizza2 = pepperoniBuilder.getPizza(); // Отримуємо результат
        System.out.println("Готова: " + pizza2);
        
        System.out.println("\n===== 2. Builder БЕЗ Director (Pizza) =====");
        PizzaFluent customPizza = new PizzaFluent.Builder()
                .dough("тонке")
                .sauce("томатний")
                .cheese(true)
                .mushrooms(true)
                .olives(false)
                .build(); // Об'єкт створюється тут
        System.out.println("Готова: " + customPizza);
        
        System.out.println("\n===== 3. Builder БЕЗ Director (SmartHome) =====");
        SmartHome home = new SmartHome.Builder()
                .addLights(5)
                .addThermostat(true)
                .addSecuritySystem(true)
                .addCameras(false)
                .build();
        System.out.println(home);
        
        System.out.println("\n===== 4. Builder (HTTP Request) =====");
        HttpRequest request = new HttpRequest.Builder()
                .method("POST")
                .url("/api/users")
                .auth(true)
                .timeout(5000)
                .build();
        System.out.println(request);
        
        // HttpRequest request = new HttpRequest("POST", "api/users", true, true, null, 5000);
        
        // new Object(true, false, null, 15, "test", null, null)
    }
}

// ==========================================
// КЛАСИ ДЛЯ ПРИКЛАДУ 1: Builder З Director
// ==========================================

// Product
class Pizza {
	private String name;
    private String dough;
    private String sauce;
    private boolean cheese;
    private boolean mushrooms;
    private boolean olives;
    private boolean pepperoni;

    public void setName(String name) { this.name = name; }
    public void setDough(String dough) { this.dough = dough; }
    public void setSauce(String sauce) { this.sauce = sauce; }
    public void setCheese(boolean cheese) { this.cheese = cheese; }
    public void setMushrooms(boolean mushrooms) { this.mushrooms = mushrooms; }
    public void setOlives(boolean olives) { this.olives = olives; }
    public void setPepperoni(boolean pepperoni) { this.pepperoni = pepperoni; }

    @Override
    public String toString() {
        return "Pizza " + name + ": " + dough + ", " + sauce + 
               ", cheese=" + cheese + ", mushrooms=" + mushrooms + 
               ", olives=" + olives + ", pepperoni=" + pepperoni;
    }
}

// Abstract Builder
abstract class PizzaBuilder {
    protected Pizza pizza;

    public PizzaBuilder() {
        pizza = new Pizza();
    }

    public abstract void buildName();
    public abstract void buildDough();
    public abstract void buildSauce();
    public abstract void buildToppings();

    public Pizza getPizza() {
        return pizza;
    }
}

// Concrete Builder 1
class MargaritaBuilder extends PizzaBuilder {
	
	@Override
	public void buildName() { pizza.setName("Margarita"); }
	
    @Override
    public void buildDough() { pizza.setDough("тонке тісто"); }
    
    @Override
    public void buildSauce() { pizza.setSauce("томатний соус"); }
    
    @Override
    public void buildToppings() {
        pizza.setCheese(true);
        pizza.setMushrooms(false);
        pizza.setOlives(false);
        pizza.setPepperoni(false);
    }
}

// Concrete Builder 2
class PepperoniBuilder extends PizzaBuilder {
	
	@Override
    public void buildName() { pizza.setName("Pepperoni"); }
	
    @Override
    public void buildDough() { pizza.setDough("класичне тісто"); }
    
    @Override
    public void buildSauce() { pizza.setSauce("гострий томатний соус"); }
    
    @Override
    public void buildToppings() {
        pizza.setCheese(true);
        pizza.setPepperoni(true);
        pizza.setMushrooms(false);
        pizza.setOlives(false);
    }
}

// Director
class Cook {
    public void makePizza(PizzaBuilder builder) {
    	builder.buildName();
        builder.buildDough();
        builder.buildSauce();
        builder.buildToppings();
    }
}

// ==========================================
// КЛАСИ ДЛЯ ПРИКЛАДУ 2: Builder БЕЗ Director
// ==========================================

class PizzaFluent {
    private String dough;
    private String sauce;
    private boolean cheese;
    private boolean mushrooms;
    private boolean olives;

    // Приватний конструктор, приймає внутрішній Builder
    private PizzaFluent(Builder builder) {
        this.dough = builder.dough;
        this.sauce = builder.sauce;
        this.cheese = builder.cheese;
        this.mushrooms = builder.mushrooms;
        this.olives = builder.olives;
    }

    // Статичний вкладений клас Builder
    public static class Builder {
        private String dough;
        private String sauce;
        private boolean cheese;
        private boolean mushrooms;
        private boolean olives;

        public Builder() {}

        public Builder dough(String dough) {
            this.dough = dough;
            return this; // Повертає самого себе для ланцюжка
        }

        public Builder sauce(String sauce) {
            this.sauce = sauce;
            return this;
        }

        public Builder cheese(boolean value) {
            this.cheese = value;
            return this;
        }

        public Builder mushrooms(boolean value) {
            this.mushrooms = value;
            return this;
        }

        public Builder olives(boolean value) {
            this.olives = value;
            return this;
        }

        // Кінцевий метод, що створює реальний об'єкт
        public PizzaFluent build() {
            return new PizzaFluent(this);
        }
    }

    @Override
    public String toString() {
        return "PizzaFluent: " + dough + ", " + sauce +
               ", cheese=" + cheese + ", mushrooms=" + mushrooms +
               ", olives=" + olives;
    }
}

// ==========================================
// КЛАСИ ДЛЯ ПРИКЛАДУ 3: SmartHome
// ==========================================

class SmartHome {
    private int lights;
    private boolean security;
    private boolean thermostat;
    private boolean cameras;

    public static class Builder {
        private SmartHome home = new SmartHome();

        public Builder addLights(int count) {
            home.lights = count;
            return this;
        }

        public Builder addSecuritySystem(boolean value) {
            home.security = value;
            return this;
        }
        
        public Builder addThermostat(boolean value) {
            home.thermostat = value;
            return this;
        }
        
        public Builder addCameras(boolean value) {
            home.cameras = value;
            return this;
        }

        public SmartHome build() {
            return home;
        }
    }
    
    @Override
    public String toString() {
        return "SmartHome: lights=" + lights + ", security=" + security + 
               ", thermostat=" + thermostat + ", cameras=" + cameras;
    }
}

// ==========================================
// КЛАСИ ДЛЯ ПРИКЛАДУ 4: HTTP Request
// ==========================================

class HttpRequest {
    private String method;
    private String url;
    private boolean auth;
    private int timeout;

    public static class Builder {
        private HttpRequest request;

        public Builder() {
            request = new HttpRequest();
        }

        public Builder method(String method) {
            request.method = method;
            return this;
        }

        public Builder url(String url) {
            request.url = url;
            return this;
        }

        public Builder auth(boolean auth) {
            request.auth = auth;
            return this;
        }

        public Builder timeout(int timeout) {
            request.timeout = timeout;
            return this;
        }

        public HttpRequest build() {
            return request;
        }
    }

    @Override
    public String toString() {
        return "HttpRequest: " + method + " " + url +
               ", auth=" + auth + ", timeout=" + timeout + "ms";
    }
}
