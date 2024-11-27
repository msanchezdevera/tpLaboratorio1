package model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import exception.TipoMovimientoInvalidoException;

public class Movimiento {

	public static final String TIPO_DEPOSITO = "Depósito";
    public static final String TIPO_EXTRACCION = "Extracción";
    public static final String TIPO_TRANSFERENCIA_DEBITO = "Transferencia Débito";
    public static final String TIPO_TRANSFERENCIA_CREDITO = "Transferencia Crédito";
    public static final String TIPO_PAGO_TARJETA = "Pago de Tarjeta";
    public static final String TIPO_DEBITO_AUTOMATICO = "Débito Automático";
    public static final String TIPO_ACREDITACION_INTERES = "Acreditación de Intereses";

    public static final Set<String> TIPOS_MOVIMIENTO_VALIDOS = new HashSet<>();

    static {
        TIPOS_MOVIMIENTO_VALIDOS.add(TIPO_DEPOSITO);
        TIPOS_MOVIMIENTO_VALIDOS.add(TIPO_EXTRACCION);
        TIPOS_MOVIMIENTO_VALIDOS.add(TIPO_TRANSFERENCIA_DEBITO);
        TIPOS_MOVIMIENTO_VALIDOS.add(TIPO_TRANSFERENCIA_CREDITO);
        TIPOS_MOVIMIENTO_VALIDOS.add(TIPO_PAGO_TARJETA);
        TIPOS_MOVIMIENTO_VALIDOS.add(TIPO_DEBITO_AUTOMATICO);
        TIPOS_MOVIMIENTO_VALIDOS.add(TIPO_ACREDITACION_INTERES);
    }

	private Integer id;
	private String tipo;
	private Date fecha;
	private double monto;
	private String descripcion;

	private Integer cuentaId;
	private Integer tarjetaId;
	private Integer usuarioId;

	private CuentaBancaria cuenta;
	private Tarjeta tarjeta;
	private Usuario usuario;

	private double saldoPrevio;
	private double saldoPosterior;

	public Movimiento(String tipo, Date fecha, double monto, String descripcion, Integer cuentaId, Integer tarjetaId, Integer usuarioId, double saldoPrevio, double saldoPosterior)
            throws TipoMovimientoInvalidoException {
        validarTipoMovimiento(tipo);
        this.tipo = tipo;
        this.fecha = fecha;
        this.monto = monto;
        this.descripcion = descripcion;
        this.cuentaId = cuentaId;
        this.tarjetaId = tarjetaId;
        this.usuarioId = usuarioId;
        this.saldoPrevio = saldoPrevio;
        this.saldoPosterior = saldoPosterior;
    }

	public Movimiento(Integer id, String tipo, Date fecha, double monto, String descripcion, Integer cuentaId, Integer tarjetaId, Integer usuarioId, double saldoPrevio, double saldoPosterior)
            throws TipoMovimientoInvalidoException {
        validarTipoMovimiento(tipo);
        this.id = id;
        this.tipo = tipo;
        this.fecha = fecha;
        this.monto = monto;
        this.descripcion = descripcion;
        this.cuentaId = cuentaId;
        this.tarjetaId = tarjetaId;
        this.usuarioId = usuarioId;
        this.saldoPrevio = saldoPrevio;
        this.saldoPosterior = saldoPosterior;
    }

	public boolean esDebito() {
		return tipo.equals(TIPO_EXTRACCION) || tipo.equals(TIPO_TRANSFERENCIA_DEBITO) || tipo.equals(TIPO_PAGO_TARJETA)
				|| tipo.equals(TIPO_DEBITO_AUTOMATICO);
	}

	public boolean esCredito() {
		return tipo.equals(TIPO_DEPOSITO) || tipo.equals(TIPO_ACREDITACION_INTERES) || tipo.equals(TIPO_TRANSFERENCIA_CREDITO);
	}

	private void validarTipoMovimiento(String tipoMovimiento) throws TipoMovimientoInvalidoException {
		if (!TIPOS_MOVIMIENTO_VALIDOS.contains(tipoMovimiento)) {
			throw new TipoMovimientoInvalidoException("Tipo de movimiento no válido: " + tipoMovimiento);
		}
	}
	public Integer getTarjetaId() {
		return tarjetaId;
	}

	public void setTarjetaId(Integer tarjetaId) {
		this.tarjetaId = tarjetaId;
	}

	public Integer getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(Integer usuarioId) {
		this.usuarioId = usuarioId;
	}

	public Tarjeta getTarjeta() {
		return tarjeta;
	}

	public void setTarjeta(Tarjeta tarjeta) {
		this.tarjeta = tarjeta;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public double getMonto() {
		return monto;
	}

	public void setMonto(double monto) {
		this.monto = monto;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public double getSaldoPrevio() {
		return saldoPrevio;
	}

	public void setSaldoPrevio(double saldoPrevio) {
		this.saldoPrevio = saldoPrevio;
	}

	public double getSaldoPosterior() {
		return saldoPosterior;
	}

	public void setSaldoPosterior(double saldoPosterior) {
		this.saldoPosterior = saldoPosterior;
	}

	public Integer getCuentaId() {
		return cuentaId;
	}

	public void setCuentaId(Integer cuentaId) {
		this.cuentaId = cuentaId;
	}

	public CuentaBancaria getCuenta() {
		return cuenta;
	}

	public void setCuenta(CuentaBancaria cuenta) {
		this.cuenta = cuenta;
	}

	@Override
	public String toString() {
		return "Movimiento{id=" + id + ", tipo='" + tipo + "', fecha=" + fecha + ", monto=" + monto + ", descripcion='"
				+ descripcion + "', cuentaId=" + cuentaId
				+ ", tarjetaId=" + tarjetaId + ", usuarioId=" + usuarioId + ", saldoPrevio=" + saldoPrevio
				+ ", saldoPosterior=" + saldoPosterior + "}";
	}
}