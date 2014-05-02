package nc.notus.entity;

/**
 * Implements most profitable router entity
 * @author Andrey Ilin
 */
public class MostProfitableRouterReportData {

    int id;
    String name;
    int portQuantity;
    int profit;

    public MostProfitableRouterReportData() {

    }

    public MostProfitableRouterReportData(int id, String name, int portQuantity, int profit) {
        this.id = id;
        this.name = name;
        this.portQuantity = portQuantity;
        this.profit = profit;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPortQuantity() {
        return portQuantity;
    }

    public void setPortQuantity(int portQuantity) {
        this.portQuantity = portQuantity;
    }

    public int getProfit() {
        return profit;
    }

    public void setProfit(int profit) {
        this.profit = profit;
    }

    

}
