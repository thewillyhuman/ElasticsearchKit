package ESKit;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

public class ESKProperties {

	private String _clusterName = "elasticsearch", _hostName = "localhost", _indexName;
	private int _port = 9300;
	private Client _client;
	private TransportClient _transportClient;
	
	// ----------- END OF FIELDS -------------

	public ESKProperties() {}

	public ESKProperties( String clusterName ) {
		setClusterName( clusterName );
	}

	public ESKProperties( String clusterName, int port ) {
		this( clusterName );
		setPort( port );
	}

	public ESKProperties( String clusterName, int port, String hostName ) {
		this( clusterName, port );
		this._hostName = hostName;
	}
	
	public ESKProperties( String clusterName, int port, String hostName, String indexName ) {
		this( clusterName, port, hostName );
		this._indexName = indexName;
	}
	
	// ----------- END OF CONSTRUCTORS -------------
	
	public String getClusterName() {
		return _clusterName;
	}

	public ESKProperties setClusterName( String _clusterName ) {
		this._clusterName = _clusterName;
		return this;
	}

	public String getHostName() {
		return _hostName;
	}

	public ESKProperties setHostName( String _hostName ) {
		this._hostName = _hostName;
		return this;
	}

	public String getIndexName() {
		return _indexName;
	}

	public ESKProperties setIndexName( String _indexName ) {
		this._indexName = _indexName;
		return this;
	}

	public int getPort() {
		return _port;
	}

	public ESKProperties setPort( int _port ) {
		this._port = _port;
		return this;
	}

	public Client getClient() {
		return _client;
	}

	public ESKProperties setClient( Client _client ) {
		this._client = _client;
		return this;
	}

	public TransportClient getTransportClient() {
		return _transportClient;
	}

	public ESKProperties setTransportClient( TransportClient _transportClient ) {
		this._transportClient = _transportClient;
		return this;
	}

	// ----------- END OF PUBLIC METHODS -------------

	// ----------- END OF PROTECTED METHODS -------------
	
	/**
	 * 
	 * @return the client once the connection is established.
	 * @throws UnknownHostException
	 */
	@SuppressWarnings("unused")
	private Client connect() throws UnknownHostException {
		_transportClient = new PreBuiltTransportClient( Settings.builder()
				.put( "cluster.name", getClusterName() )
				.put( "client.transport.sniff", false )
				.put( "client.transport.ping_timeout", 20, TimeUnit.SECONDS )
				.build() );

		_transportClient.addTransportAddress( new TransportAddress(
				InetAddress.getByName( getHostName() ), getPort() ) );

		this._client = _transportClient;
		return getClient();
	}
	
	/**
	 * Disconnects the client from the cluster.
	 */
	@SuppressWarnings("unused")
	private void disconnect() {
		this._client.close();
	}

}
