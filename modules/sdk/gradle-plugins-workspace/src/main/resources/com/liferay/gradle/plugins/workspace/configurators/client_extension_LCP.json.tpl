{
	"kind": "Deployment",
	"id": "__CLIENT_EXTENSION_ID__",
	"cpu": 0.1,
	"scale": 1,
	"memory": 50,
	"loadBalancer": {
		"targetPort": 80,
		"cdn": true
	},
	"environments": {
		"infra": {
			"deploy": false
		},
		"dev": {
			"loadBalancer": {
				"targetPort": 80,
				"cdn": false
			}
		}
	}
}