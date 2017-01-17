package com.hanson.elasticsearch.client;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsRequest;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.get.MultiGetItemResponse;
import org.elasticsearch.action.get.MultiGetRequestBuilder;
import org.elasticsearch.action.get.MultiGetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.Requests;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.json.JSONObject;

public class ESClient {
	private String ip;
	private int port;
	private Client client;
	private Logger logger = Logger.getLogger(this.getClass());
	private final int RET_SUCCESS = 1;
	private final int RET_FAILED = 0;

	public ESClient(String ip, int port) {
		this.ip = ip;
		this.port = port;
	}

	public int connect() {
		int ret = RET_FAILED;
		try {
			client = TransportClient.builder().build()
					.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(ip), port));
			return RET_SUCCESS;
		} catch (Exception e) {
			logger.error("connect ES error:", e);
		}
		return ret;
	}

	public boolean create(String indicesName, String typeName) {
		try {
			if (client != null) {
				IndicesExistsResponse existsRes = client.admin().indices().exists(new IndicesExistsRequest(indicesName)).actionGet();
				if(!existsRes.isExists()){
					JSONObject dataBaseInfo = new JSONObject();
					dataBaseInfo.put("createtime", System.currentTimeMillis());
					dataBaseInfo.put("createuser", System.getProperty("user.name"));
					Settings settings = Settings.settingsBuilder().put("number_of_shards", 1).put("number_of_replicas", 0)
							.build();
					client.admin().indices().prepareCreate(indicesName).setSettings(settings).get();
					IndexResponse res = client.prepareIndex(indicesName, typeName, "createInfo").setSource(dataBaseInfo.toString())
							.get();
					return res.isCreated();
				}
			}
		} catch (Exception e) {
			logger.error("create indices error:", e);
		}
		return false;
	}

	public void createMapping(String indices, String mappingType,JSONObject properties) {
		try {
			if (client != null) {
				if (client != null) {
					JSONObject mappingJson = new JSONObject();
					mappingJson.put(indices, properties);
					PutMappingRequest mapping = Requests.putMappingRequest(indices).type(mappingType).source(mappingJson);
					client.admin().indices().putMapping(mapping).actionGet();
				}
			}
		} catch (Exception e) {
			logger.error("create mapping error:", e);
		}
	}
	
	public boolean put(String indicesName, String typeName,JSONObject jsonObject) throws Exception {
		try {
			if (client != null) {
				IndexResponse res = client.prepareIndex(indicesName, typeName,String.valueOf(jsonObject.getInt("id"))).setSource(jsonObject.toString()).get();
				return res.isCreated();
			}
		} catch (Exception e) {
			logger.error("create mapping error:", e);
		}
		return false;
	}

	public List<JSONObject> get(String indicesName, String typeName, List<String> documentIds) {
		try {
			if (client != null) {
				MultiGetRequestBuilder req = client.prepareMultiGet();
				for (String documentId : documentIds) {
					req.add(indicesName, documentId);
				}
				MultiGetResponse res = req.get();
				List<JSONObject> ret = new ArrayList<JSONObject>();
				for (MultiGetItemResponse itemResponse : res) {
					GetResponse response = itemResponse.getResponse();
					if (response.isExists()) {
						ret.add(new JSONObject(response.getSourceAsString()));
					}
				}
				return ret;
			}
		} catch (Exception e) {
			logger.error("get document error:", e);
		}
		return null;
	}

	public JSONObject get(String indicesName, String typeName, String documentId) {
		try {
			if (client != null) {
				GetResponse res = client.prepareGet(indicesName, typeName, documentId).get();
				return new JSONObject(res.getSourceAsString());
			}
		} catch (Exception e) {
			logger.error("get document error:", e);
		}
		return null;
	}

	public JSONObject get(String indicesName, String typeName, String documentId, String... fileds) {
		try {
			if (client != null) {
				GetResponse res = client.prepareGet(indicesName, typeName, documentId).setFields(fileds).get();
				return new JSONObject(res.getSourceAsString());
			}
		} catch (Exception e) {
			logger.error("get document error:", e);
		}
		return null;
	}

	public int delete(String indicesName, String typeName, String documentId) {
		try {
			if (client != null) {
				client.prepareDelete(indicesName, typeName, documentId).get();
				return RET_SUCCESS;
			}
		} catch (Exception e) {
			logger.error("get document error:", e);
		}
		return RET_FAILED;
	}

	public int update(String indicesName, String typeName, String documentId, JSONObject json) {
		try {
			if (client != null) {
				UpdateRequest req = new UpdateRequest(indicesName, typeName, documentId).doc(json);
				client.update(req).get();
				return RET_SUCCESS;
			}
		} catch (Exception e) {
			logger.error("get document error:", e);
		}
		return RET_FAILED;
	}

	public int disconnect() {
		int ret = RET_FAILED;
		try {
			if (client != null) {
				client.close();
				return RET_SUCCESS;
			}
		} catch (Exception e) {
			logger.error("disconnect ES error:", e);
		}
		return ret;
	}
}
