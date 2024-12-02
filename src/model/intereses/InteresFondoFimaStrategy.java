	package model.intereses;

public class InteresFondoFimaStrategy implements InteresStrategy {

	private static final double TASA_INTERES_ANUAL = 5.0;

    @Override
    public double calcularInteres(double saldo) {
        double tasaMensual = TASA_INTERES_ANUAL / 12 / 100;
        return saldo * tasaMensual;
    }

}
