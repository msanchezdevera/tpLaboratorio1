package model;

import java.util.HashSet;
import java.util.Set;

import exception.TipoTarjetaInvalidaException;

public class Tarjeta {

	public static final String TIPO_CREDITO = "Crédito";
	public static final String TIPO_DEBITO = "Débito";

	public static final Set<String> TIPOS_TARJETA_VALIDOS = new HashSet<String>();

	static {
		TIPOS_TARJETA_VALIDOS.add(TIPO_CREDITO);
		TIPOS_TARJETA_VALIDOS.add(TIPO_DEBITO);
	}

	private Integer id;
	private String numeroTarjeta;
	private String tipo;
	private double limiteCredito;
	private double saldoUtilizado;
	private double saldoDisponible;
	private Usuario usuario;
	private Integer usuarioId;

	public Tarjeta(String numeroTarjeta, String tipo, double limiteCredito, double saldoUtilizado, Integer usuarioId)
			throws TipoTarjetaInvalidaException {
		validarTipoTarjeta(tipo);
		this.numeroTarjeta = numeroTarjeta;
		this.tipo = tipo;
		this.limiteCredito = limiteCredito;
		this.saldoUtilizado = saldoUtilizado;
		this.saldoDisponible = limiteCredito - saldoUtilizado;
		this.usuarioId = usuarioId;
	}

	public Tarjeta(int id, String numeroTarjeta, String tipo, double limiteCredito, double saldoUtilizado,
			Integer usuarioId) throws TipoTarjetaInvalidaException {
		validarTipoTarjeta(tipo);
		this.id = id;
		this.numeroTarjeta = numeroTarjeta;
		this.tipo = tipo;
		this.limiteCredito = limiteCredito;
		this.saldoUtilizado = saldoUtilizado;
		this.saldoDisponible = limiteCredito - saldoUtilizado;
		this.usuarioId = usuarioId;
	}

	private void validarTipoTarjeta(String tipoTarjeta) throws TipoTarjetaInvalidaException {
		if (!TIPOS_TARJETA_VALIDOS.contains(tipoTarjeta)) {
			throw new TipoTarjetaInvalidaException("Tipo de tarjeta no válido: " + tipoTarjeta);
		}
	}
	
	public void debitar(double monto) {
        if (monto > saldoDisponible) {
            throw new IllegalArgumentException("El monto a debitar excede el saldo disponible.");
        }
        this.saldoUtilizado += monto;
        actualizarSaldoDisponible();
    }

    private void actualizarSaldoDisponible() {
        this.saldoDisponible = this.limiteCredito - this.saldoUtilizado;
    }

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNumeroTarjeta() {
		return numeroTarjeta;
	}

	public void setNumeroTarjeta(String numeroTarjeta) {
		this.numeroTarjeta = numeroTarjeta;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) throws TipoTarjetaInvalidaException {
		validarTipoTarjeta(tipo);
		this.tipo = tipo;
	}

	public double getLimiteCredito() {
		return limiteCredito;
	}

	public void setLimiteCredito(double limiteCredito) {
		this.limiteCredito = limiteCredito;
		actualizarSaldoDisponible();
	}

	public double getSaldoUtilizado() {
		return saldoUtilizado;
	}

	public void setSaldoUtilizado(double saldoUtilizado) {
		this.saldoUtilizado = saldoUtilizado;
		actualizarSaldoDisponible();
	}

	public double getSaldoDisponible() {
		return saldoDisponible;
	}

	public Integer getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(Integer usuarioId) {
		this.usuarioId = usuarioId;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	@Override
	public String toString() {
		return "Tarjeta{id=" + id + ", numeroTarjeta='" + numeroTarjeta + "', tipo='" + tipo + "', limiteCredito="
				+ limiteCredito + ", saldoUtilizado=" + saldoUtilizado + ", saldoDisponible=" + saldoDisponible
				+ ", usuarioId=" + usuarioId + "}";
	}

}